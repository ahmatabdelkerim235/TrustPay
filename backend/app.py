from flask import Flask, request, jsonify
from flask_cors import CORS
from pymongo import MongoClient
import random
from datetime import datetime

app = Flask(__name__)
CORS(app)

# -------------------------------
# MongoDB connection
# -------------------------------
client = MongoClient("mongodb://localhost:27017/")
db = client["trustpay_db"]

users = db["users"]
transactions = db["transactions"]


@app.route("/")
def home():
    return "TrustPay Backend Running"


# -------------------------------
# Generate Unique UPI ID
# -------------------------------
def generate_upi(name):

    username = name.lower().replace(" ", ".")

    while True:
        random_number = random.randint(1000, 9999)
        upi_id = f"{username}{random_number}@trustpay"

        existing = users.find_one({"upi_id": upi_id})

        if not existing:
            return upi_id


# -------------------------------
# Register API
# -------------------------------
@app.route("/register", methods=["POST"])
def register():

    data = request.json

    name = data.get("name")
    email = data.get("email")
    mobile = data.get("mobile")
    password = data.get("password")
    upi_pin = data.get("upi_pin")

    if not upi_pin or len(upi_pin) != 4:
        return jsonify({"message": "UPI PIN must be 4 digits"}), 400

    if users.find_one({"email": email}):
        return jsonify({"message": "Email already registered"}), 400

    if users.find_one({"mobile": mobile}):
        return jsonify({"message": "Mobile already registered"}), 400

    upi_id = generate_upi(name)

    user = {
        "name": name,
        "email": email,
        "mobile": mobile,
        "password": password,
        "upi_id": upi_id,
        "upi_pin": upi_pin
    }

    users.insert_one(user)

    return jsonify({
        "message": "User registered successfully",
        "upi_id": upi_id
    }), 200


# -------------------------------
# Login API
# -------------------------------
@app.route("/login", methods=["POST"])
def login():

    data = request.json

    email = data.get("email")
    password = data.get("password")

    user = users.find_one({"email": email})

    if not user:
        return jsonify({"message": "User not found"}), 404

    if user["password"] != password:
        return jsonify({"message": "Invalid password"}), 401

    return jsonify({
        "message": "Login successful",
        "name": user["name"],
        "email": user["email"],
        "mobile": user["mobile"],
        "upi_id": user["upi_id"]
    }), 200


# -------------------------------
# Transaction API
# -------------------------------
@app.route("/transaction", methods=["POST"])
def make_transaction():

    data = request.json

    print("Transaction Request:", data)

    sender_upi = data.get("sender_upi")
    receiver_input = data.get("receiver_upi")
    amount = data.get("amount")
    entered_pin = data.get("upi_pin")

    # -------------------------------
    # Find Sender
    # -------------------------------
    sender = users.find_one({"upi_id": sender_upi})

    if not sender:
        return jsonify({"message": "Sender not found"}), 404

    # -------------------------------
    # Verify PIN (FIXED)
    # -------------------------------
    if str(sender["upi_pin"]) != str(entered_pin):
        return jsonify({"message": "Invalid UPI PIN"}), 401

    # -------------------------------
    # Find Receiver
    # -------------------------------
    receiver = users.find_one({"upi_id": receiver_input})

    if not receiver:
        receiver = users.find_one({"mobile": receiver_input})

    if not receiver:
        return jsonify({"message": "Receiver not found"}), 404

    receiver_upi = receiver["upi_id"]

    # -------------------------------
    # Store Transaction
    # -------------------------------
    transaction = {
        "sender_upi": sender_upi,
        "receiver_upi": receiver_upi,
        "amount": amount,
        "timestamp": datetime.now(),
        "status": "SUCCESS"
    }

    transactions.insert_one(transaction)

    return jsonify({
        "message": "Transaction successful"
    }), 200


# -------------------------------
# Run Server
# -------------------------------
if __name__ == "__main__":
    app.run(host="0.0.0.0", port=5000, debug=True)