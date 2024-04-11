from fastapi import APIRouter


router = APIRouter(prefix="/houseshares", tags=["House Shares"])

@router.get("/{houseshare_id}")
def get_houseshare(houseshare_id: int):
    return {}

@router.get("/user/{user_id}")
def get_user_houseshares(user_id: int):
    return {}

@router.post("")
def add_houseshare():
    return {}

@router.put("/{houseshare_id}")
def update_houseshare(houseshare_id: int):
    return {}

@router.delete("/{houseshare_id}")
def delete_houseshare(houseshare_id: int):
    return {}