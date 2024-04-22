import models
from database import engine
from fastapi import FastAPI
from fastapi.middleware.cors import CORSMiddleware
from routers import auth, dues, events, groceries, house_shares, tasks, users

models.Base.metadata.create_all(bind=engine)

app = FastAPI(title="ColoGestionAPI", redoc_url=None, docs_url="/docs")
app.include_router(auth.router)
app.include_router(dues.router)
app.include_router(events.router)
app.include_router(groceries.router)
app.include_router(house_shares.router)
app.include_router(tasks.router)
app.include_router(users.router)


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
