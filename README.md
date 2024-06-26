# ColloGestion

## Prerequisites
Before you begin, ensure you have the following prerequisites installed and set up:
- Python 3 and pip installed
- An SQL database
- Android studio

## Installation and setup

Clone the repository and navigate to the project directory:
```bash
git clone https://github.com/Wiqiro/ColloGestion
cd ColloGestion
```

Import the project in Android Studio

Install requirements for API:
```bash
cd api
pip install -r requirements.txt
```

Rename `.env.example` to `.env` and replace the values with your own (database url and token secret)

## Running the app

1. Run your database

2. Run the API using uvicorn:
```
uvicorn main:app --reload
```
If you are using windows, you may have to add uvicorn to the path.

3. Run the app from Android Studio
