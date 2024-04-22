from typing import List, Optional

from pydantic import BaseModel


class HouseShareBase(BaseModel):
    name: str
    image: str


class HouseShareCreate(HouseShareBase):
    pass


class HouseShare(HouseShareBase):
    id: int
    users: List["UserBase"]

    class Config:
        from_attributes = True


class UserBase(BaseModel):
    first_name: str
    last_name: str
    email: str
    phone: str


class UserCreate(UserBase):
    password: str


class User(UserBase):
    id: int
    profile_picture: str
    house_shares: List[HouseShareBase]
    tasks: List["TaskBase"]

    class Config:
        from_attributes = True


class TaskBase(BaseModel):
    name: str
    deadline: int
    done: bool
    assignee_id: int
