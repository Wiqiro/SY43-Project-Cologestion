import models
from database import get_db
from fastapi import APIRouter, Depends
from requests import Session

router = APIRouter(prefix="/groceries", tags=["Groceries"])


@router.get("/user/{user_id}")
def get_user_groceries(user_id: int, db: Session = Depends(get_db)):
    return (
        db.query(models.GroceryList)
        .filter(models.GroceryList.assignee_id == user_id)
        .all()
    )


@router.get("/house_share/{house_share_id}")
def get_house_share_groceries(house_share_id: int, db: Session = Depends(get_db)):
    return (
        db.query(models.GroceryList)
        .filter(models.GroceryList.house_share_id == house_share_id)
        .all()
    )


@router.post("")
def add_grocery(db: Session = Depends(get_db)):
    return {}


@router.put("/{grocery_id}")
def update_grocery(grocery_id: int, db: Session = Depends(get_db)):
    return {}


@router.delete("/{grocery_id}")
def delete_grocery(grocery_id: int, db: Session = Depends(get_db)):
    return {}
