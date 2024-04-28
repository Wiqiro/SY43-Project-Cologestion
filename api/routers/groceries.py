from typing import List

import models
import schemas
from database import get_db
from fastapi import APIRouter, Depends, HTTPException
from requests import Session
from sqlalchemy.orm.exc import NoResultFound
from utils import get_current_user

router = APIRouter(prefix="/groceries", tags=["Groceries"])


@router.get("/user/{user_id}", response_model=List[schemas.GroceryList])
def get_user_groceries(
    user_id: int,
    db: Session = Depends(get_db),
    current_user_id: str = Depends(get_current_user),
):
    try:
        return (
            db.query(models.GroceryList)
            .filter(models.GroceryList.assignee_id == user_id)
            .all()
        )
    except NoResultFound:
        raise HTTPException(status_code=404, detail="User not found")
    except Exception as e:
        raise HTTPException(status_code=500, detail=str(e))


@router.get("/house_share/{house_share_id}", response_model=List[schemas.GroceryList])
def get_house_share_groceries(
    house_share_id: int,
    db: Session = Depends(get_db),
    current_user_id: str = Depends(get_current_user),
):
    try:
        return (
            db.query(models.GroceryList)
            .filter(models.GroceryList.house_share_id == house_share_id)
            .all()
        )
    except NoResultFound:
        raise HTTPException(status_code=404, detail="House share not found")
    except Exception as e:
        raise HTTPException(status_code=500, detail=str(e))


@router.post("", response_model=schemas.GroceryList)
def add_grocery(
    grocery_list: schemas.GroceryListCreate,
    db: Session = Depends(get_db),
    current_user_id: str = Depends(get_current_user),
):
    try:
        new_grocery = models.GroceryList(**grocery_list.model_dump())
        db.add(new_grocery)
        db.commit()
        db.refresh(new_grocery)
        return new_grocery
    except Exception as e:
        raise HTTPException(status_code=500, detail=str(e))


@router.post("/item", response_model=schemas.GroceryItem)
def add_grocery_item(
    grocery_item: schemas.GroceryItemCreate,
    db: Session = Depends(get_db),
    current_user_id: str = Depends(get_current_user),
):
    try:
        new_grocery_item = models.GroceryItem(**grocery_item.model_dump())
        db.add(new_grocery_item)
        db.commit()
        db.refresh(new_grocery_item)
        return new_grocery_item
    except Exception as e:
        raise HTTPException(status_code=500, detail=str(e))


@router.put("/{grocery_id}", response_model=schemas.GroceryList)
def update_grocery(
    grocery_id: int,
    grocery_list: schemas.GroceryListCreate,
    db: Session = Depends(get_db),
    current_user_id: str = Depends(get_current_user),
):
    try:
        new_grocery = grocery_list.model_dump()
        updated_rows = (
            db.query(models.GroceryList).filter_by(id=grocery_id).update(new_grocery)
        )
        db.commit()
        if updated_rows == 0:
            raise HTTPException(status_code=404, detail="Grocery list not found")
        return db.query(models.GroceryList).get(grocery_id)
    except Exception as e:
        raise HTTPException(status_code=500, detail=str(e))


@router.put("/item/{grocery_item_id}", response_model=schemas.GroceryItem)
def update_grocery_item(
    grocery_item_id: int,
    grocery_item: schemas.GroceryItemCreate,
    db: Session = Depends(get_db),
    current_user_id: str = Depends(get_current_user),
):
    try:
        new_grocery_item = grocery_item.model_dump()
        updated_rows = (
            db.query(models.GroceryItem)
            .filter_by(id=grocery_item_id)
            .update(new_grocery_item)
        )
        db.commit()
        if updated_rows == 0:
            raise HTTPException(status_code=404, detail="Grocery item not found")
        return db.query(models.GroceryItem).get(grocery_item_id)
    except Exception as e:
        raise HTTPException(status_code=500, detail=str(e))


@router.delete("/{grocery_id}", response_model=schemas.GroceryList)
def delete_grocery(
    grocery_id: int,
    db: Session = Depends(get_db),
    current_user_id: str = Depends(get_current_user),
):
    try:
        grocery = db.query(models.GroceryList).get(grocery_id)
        db.delete(grocery)
        db.commit()
        return grocery
    except NoResultFound:
        raise HTTPException(status_code=404, detail="Grocery list not found")
    except Exception as e:
        raise HTTPException(status_code=500, detail=str(e))


@router.delete("/item/{grocery_item_id}", response_model=schemas.GroceryItem)
def delete_grocery_item(
    grocery_item_id: int,
    db: Session = Depends(get_db),
    current_user_id: str = Depends(get_current_user),
):
    try:
        grocery_item = db.query(models.GroceryItem).get(grocery_item_id)
        db.delete(grocery_item)
        db.commit()
        return grocery_item
    except NoResultFound:
        raise HTTPException(status_code=404, detail="Grocery item not found")
    except Exception as e:
        raise HTTPException(status_code=500, detail=str(e))
