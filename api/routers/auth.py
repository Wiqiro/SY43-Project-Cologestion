import models
import schemas
from database import get_db
from fastapi import APIRouter, Depends, HTTPException
from fastapi.security import OAuth2PasswordRequestForm
from requests import Session
from sqlalchemy.orm.exc import NoResultFound
from utils import get_password_hash, verify_password

router = APIRouter(prefix="/auth", tags=["Auth"])


@router.post("/token")
def login(
    form_data: OAuth2PasswordRequestForm = Depends(), db: Session = Depends(get_db)
):
    try:
        user = db.query(models.User).filter_by(email=form_data.username).one()
        if not verify_password(form_data.password, user.password_hash):
            raise HTTPException(status_code=401, detail="Incorrect password")
    except NoResultFound:
        raise HTTPException(status_code=404, detail="User not found")
    except HTTPException:
        raise
    except Exception as e:
        print(e)
        raise HTTPException(status_code=500, detail=str(e))
