import jwt
import schemas
from config import settings
from fastapi import Depends, HTTPException
from fastapi.security import OAuth2PasswordBearer
from passlib.context import CryptContext

ALGORITHM = "HS256"


oauth2_scheme = OAuth2PasswordBearer(tokenUrl="/auth/token")
pwd_context = CryptContext(schemes=["bcrypt"])


def create_token(user: schemas.User):
    payload = {
        "user_id": user.id,
    }
    return jwt.encode(payload, settings.token_secret, algorithm=ALGORITHM)


async def get_current_user(token: str = Depends(oauth2_scheme)):
    print(token)
    try:
        payload = jwt.decode(token, settings.token_secret, algorithms=[ALGORITHM])
    except jwt.DecodeError:
        raise HTTPException(status_code=401, detail="Invalid token")

    user_id = payload.get("user_id")
    if user_id is None:
        raise HTTPException(status_code=401, detail="Invalid token")
    return user_id


def verify_password(plain_password, hashed_password):
    return pwd_context.verify(plain_password, hashed_password)


def get_password_hash(password):
    return pwd_context.hash(password)
