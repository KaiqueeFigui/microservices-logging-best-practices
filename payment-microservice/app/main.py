from fastapi import FastAPI
from fastapi import Depends
from sqlalchemy.orm import Session

from .database import models

from .database.database import SessionLocal, engine
from .service import participant_service, transaction_service
from .dto import schemas
from .config.logger import logger
from .config.RequestMiddleware import RequestContextMiddleware
from .database.database import SQLALCHEMY_DATABASE_URL

print(SQLALCHEMY_DATABASE_URL)

models.Base.metadata.create_all(bind=engine)
app = FastAPI()

app.add_middleware(RequestContextMiddleware)

def get_db():
    db = SessionLocal()
    try:
        yield db
    finally:
        db.close()


@app.get("/")
def root():
    logger.info("Hello world!")
    return {"message": "Hello World"}

@app.post("/participant", response_model=schemas.Participant)
def post_participant(participantCreate: schemas.ParticipantCreate, db: Session = Depends(get_db)):
    return participant_service.create_user(participantCreate, db)

@app.put("/participant/{id}/{difference}", response_model=schemas.Participant)
def update_balance(id: int, difference: float, db: Session = Depends(get_db)):
    return participant_service.update_balance(id, difference, db)

@app.post("/transaction/transfer", response_model=schemas.Transaction)
def create_transaction(transaction_create: schemas.TransactionCreate, db: Session = Depends(get_db)):
    return transaction_service.create_transaction(transaction_create, db)