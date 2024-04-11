from fastapi import FastAPI
from fastapi.middleware.cors import CORSMiddleware

import models
from database import engine

models.Base.metadata.create_all(bind=engine)

app = FastAPI(title="ColoGestionAPI", redoc_url=None, docs_url="/docs")
# app.include_router(...)


origins = [
    # Add origins here
]

app.add_middleware(
    CORSMiddleware,
    allow_origins=origins,
    allow_credentials=True,
    allow_methods=["*"],
    allow_headers=["*"],
)
