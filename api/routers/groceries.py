from fastapi import APIRouter


router = APIRouter(prefix="/groceries", tags=["Groceries"])

@router.get("/user/{user_id}")
def get_user_groceries(user_id: int):
    return {}

@router.get("/houseshare/{houseshare_id}")
def get_houseshare_groceries(houseshare_id: int):
    return {}

@router.post("")
def add_grocery():
    return {}

@router.put("/{grocery_id}")
def update_grocery(grocery_id: int):
    return {}

@router.delete("/{grocery_id}")
def delete_grocery(grocery_id: int):
    return {}
