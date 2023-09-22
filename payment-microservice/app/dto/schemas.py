from pydantic import BaseModel

class ParticipantBase(BaseModel):
    id: int | None
    document: str
    account: str
    agency: str

class Participant(ParticipantBase):
    balance: float

    class Config:
        from_attributes = True

class ParticipantCreate(ParticipantBase):
    pass

class TransactionBase(BaseModel):
    id: int | None
    value: float
    description: str

class Transaction(TransactionBase):
    payer: Participant
    receiver: Participant
    dateTime: str 

    class Config:
        from_attributes = True

class TransactionCreate(TransactionBase):
    payer_id: int
    receiver_id: int
