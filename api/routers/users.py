from fastapi import APIRouter


router = APIRouter(prefix="/users", tags=["Users"])

@router.get("/{user_id}")
def get_user(user_id: int):
    return {}

@router.get("/houseshare/{houseshare_id}")
def get_houseshare_users(houseshare_id: int):
    return {}