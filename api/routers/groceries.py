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
    new_grocery = models.GroceryList(**grocery_list.model_dump())
    db.add(new_grocery)
    db.commit()
    db.refresh(new_grocery)
    return new_grocery


@router.post("/item", response_model=schemas.GroceryItem)
def add_grocery_item(
    grocery_item: schemas.GroceryItemCreate, db: Session = Depends(get_db)
):
    new_grocery_item = models.GroceryItem(**grocery_item.model_dump())
    db.add(new_grocery_item)
    db.commit()
    db.refresh(new_grocery_item)
    return new_grocery_item


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
    grocery = db.query(models.GroceryList).get(grocery_id)
    db.delete(grocery)
    db.commit()
    return grocery


@router.delete("/item/{grocery_item_id}", response_model=schemas.GroceryItem)
def delete_grocery_item(grocery_item_id: int, db: Session = Depends(get_db)):
    grocery_item = db.query(models.GroceryItem).get(grocery_item_id)
    db.delete(grocery_item)
    db.commit()
    return grocery_item
