from database import Base
from sqlalchemy import BigInteger, Boolean, Column, Enum, ForeignKey, Integer, String
from sqlalchemy.orm import relationship


class HouseShare(Base):
    __tablename__ = "house_share"

    id = Column(Integer, primary_key=True, index=True)
    name = Column(String(100), index=True)
    creation_date = Column(Integer, index=True)
    image = Column(String(100), index=True)

    users = relationship(
        "User",
        secondary="house_share_member",
        back_populates="house_shares",
    )


class HouseShareMember(Base):
    __tablename__ = "house_share_member"
    house_share_id = Column(Integer, ForeignKey("house_share.id"), primary_key=True)
    user_id = Column(Integer, ForeignKey("user.id"), primary_key=True)


class Event(Base):
    __tablename__ = "event"

    id = Column(Integer, primary_key=True, index=True)
    title = Column(String(100), index=True)
    date = Column(Integer, index=True)
    duration = Column(Integer, index=True)
    house_share_id = Column(Integer, ForeignKey("house_share.id"))


class Due(Base):
    __tablename__ = "due"

    id = Column(Integer, primary_key=True, index=True)
    amount = Column(Integer, index=True)
    creditor_id = Column(Integer, ForeignKey("user.id"))
    debtor_id = Column(Integer, ForeignKey("user.id"))
    house_share_id = Column(Integer, ForeignKey("house_share.id"))

    debtor = relationship(
        "User", back_populates="debtor_dues", foreign_keys=[debtor_id]
    )
    creditor = relationship(
        "User", back_populates="creditor_dues", foreign_keys=[creditor_id]
    )


class Task(Base):
    __tablename__ = "task"

    id = Column(Integer, primary_key=True, index=True)
    name = Column(String(100), index=True)
    deadline = Column(Integer, index=True)
    done = Column(Boolean, index=True)
    house_share_id = Column(Integer, ForeignKey("house_share.id"))
    assignee_id = Column(Integer, ForeignKey("user.id"))

    user = relationship("User", back_populates="tasks")


class GroceryList(Base):
    __tablename__ = "grocery_list"

    id = Column(Integer, primary_key=True, index=True)
    name = Column(String(100), index=True)
    house_share_id = Column(Integer, ForeignKey("house_share.id"))
    assignee_id = Column(Integer, ForeignKey("user.id"))


class GroceryItem(Base):
    __tablename__ = "grocery_item"

    id = Column(Integer, primary_key=True, index=True)
    name = Column(String(100), index=True)
    quantity = Column(String(100), index=True)
    bought = Column(Boolean, index=True)
    list_id = Column(Integer, ForeignKey("grocery_list.id"))


class User(Base):
    __tablename__ = "user"

    id = Column(Integer, primary_key=True, index=True)
    first_name = Column(String(100), index=True)
    last_name = Column(String(100), index=True)
    profile_picture = Column(String(100), index=True)
    email = Column(String(100), unique=True, index=True)
    phone = Column(String(14), index=True)
    password_hash = Column(String(100))

    creditor_dues = relationship(
        "Due", back_populates="creditor", foreign_keys=[Due.creditor_id]
    )
    debtor_dues = relationship(
        "Due", back_populates="debtor", foreign_keys=[Due.debtor_id]
    )
    house_shares = relationship(
        "HouseShare",
        secondary="house_share_member",
        back_populates="users",
    )
    tasks = relationship("Task", back_populates="user")
