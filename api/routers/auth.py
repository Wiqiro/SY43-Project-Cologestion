import models
import schemas
from database import get_db
from fastapi import APIRouter, Depends, HTTPException
from fastapi.security import OAuth2PasswordRequestForm
from requests import Session
from sqlalchemy.orm.exc import NoResultFound
from utils import create_token, verify_password

router = APIRouter(prefix="/auth", tags=["Auth"])


@router.post("/token", response_model=schemas.Token)
def login(
    form_data: OAuth2PasswordRequestForm = Depends(), db: Session = Depends(get_db)
):
    try:
        user = db.query(models.User).filter_by(email=form_data.username).one()
        if not verify_password(form_data.password, user.password_hash):
            raise HTTPException(status_code=401, detail="Incorrect password")
        token = create_token(user)
        return schemas.Token(access_token=token, token_type="bearer")
    except NoResultFound:
        raise HTTPException(status_code=404, detail="User not found")
    except HTTPException:
        raise
    except Exception as e:
        raise HTTPException(status_code=500, detail=str(e))
