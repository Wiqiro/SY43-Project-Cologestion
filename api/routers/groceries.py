from typing import List

import models
import schemas
from database import get_db
from fastapi import APIRouter, Depends
from requests import Session

router = APIRouter(prefix="/groceries", tags=["Groceries"])


@router.get("/user/{user_id}", response_model=List[schemas.GroceryList])
def get_user_groceries(user_id: int, db: Session = Depends(get_db)):
    return (
        db.query(models.GroceryList)
        .filter(models.GroceryList.assignee_id == user_id)
        .all()
    )


@router.get("/house_share/{house_share_id}", response_model=List[schemas.GroceryList])
def get_house_share_groceries(house_share_id: int, db: Session = Depends(get_db)):
    return (
        db.query(models.GroceryList)
        .filter(models.GroceryList.house_share_id == house_share_id)
        .all()
    )


@router.post("", response_model=schemas.GroceryList)
def add_grocery(grocery_list: schemas.GroceryListCreate, db: Session = Depends(get_db)):
    return {}


@router.post("/item", response_model=schemas.GroceryItem)
def add_grocery_item(
    grocery_item: schemas.GroceryItemCreate, db: Session = Depends(get_db)
):
    return {}


@router.put("/{grocery_id}", response_model=schemas.GroceryList)
def update_grocery(
    grocery_id: int,
    grocery_list: schemas.GroceryListCreate,
    db: Session = Depends(get_db),
):
    return {}


@router.put("/item/{grocery_item_id}", response_model=schemas.GroceryItem)
def update_grocery_item(
    grocery_item_id: int,
    grocery_item: schemas.GroceryItemCreate,
    db: Session = Depends(get_db),
):
    return {}


@router.delete("/{grocery_id}", response_model=schemas.GroceryList)
def delete_grocery(grocery_id: int, db: Session = Depends(get_db)):
    return {}


@router.delete("/item/{grocery_item_id}", response_model=schemas.GroceryItem)
def delete_grocery_item(grocery_item_id: int, db: Session = Depends(get_db)):
    return {}
