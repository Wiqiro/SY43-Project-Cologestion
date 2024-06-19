from typing import List, Optional

from pydantic import BaseModel, field_validator


class HouseShareBase(BaseModel):
    name: str


class HouseShareCreate(HouseShareBase):
    pass


class HouseShare(HouseShareBase):
    id: int
    creation_date: int
    users: List["UserBaseWithId"]

    class Config:
        from_attributes = True


class UserBase(BaseModel):
    first_name: str
    last_name: str
    email: str
    phone: str


class UserBaseWithId(UserBase):
    id: int


class UserCreate(UserBase):
    password: str


class User(UserBase):
    id: int
    house_shares: List[HouseShare]

    class Config:
        from_attributes = True


class UserPasswordChange(BaseModel):
    new_password: str
    old_password: str


class Token(BaseModel):
    access_token: str
    token_type: str


class TaskBase(BaseModel):
    name: str
    deadline: Optional[int] = None
    done: bool = False
    assignee_id: int
    house_share_id: int


class TaskCreate(TaskBase):
    pass


class Task(TaskBase):
    id: int

    class Config:
        from_attributes = True


class DueBase(BaseModel):
    amount: float
    title: str
    creditor_id: int
    debtor_id: int
    house_share_id: int


class DueCreate(DueBase):
    pass


class Due(DueBase):
    id: int

    debtor_name: str
    creditor_name: str

    class Config:
        from_attributes = True


class EventBase(BaseModel):
    title: str
    date: int
    duration: int
    house_share_id: int


class EventCreate(EventBase):
    pass


class Event(EventBase):
    id: int

    class Config:
        from_attributes = True


class GroceryItemBase(BaseModel):
    name: str
    quantity: int
    bought: bool = False


class GroceryItemCreate(GroceryItemBase):
    list_id: int
    pass


class GroceryItem(GroceryItemBase):
    id: int


class GroceryListBase(BaseModel):
    name: str
    house_share_id: int


class GroceryListCreate(GroceryListBase):
    pass


class GroceryList(GroceryListBase):
    id: int
    items: List[GroceryItem]

    class Config:
        from_attributes = True
