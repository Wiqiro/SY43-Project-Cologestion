from fastapi import APIRouter
from requests import Session

router = APIRouter(prefix="/auth", tags=["Auth"])
