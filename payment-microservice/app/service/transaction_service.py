from sqlalchemy.orm import Session
from fastapi import HTTPException
import moment

from .participant_service import update_balance
from ..dto import schemas
from ..database import models
from ..config.logger import logger

def create_transaction(transaction_create: schemas.TransactionCreate, db: Session):
    logger.info("Creating transaction")
    payer = db.query(models.Participant).filter(models.Participant.id == transaction_create.payer_id).first()
    receiver = db.query(models.Participant).filter(models.Participant.id == transaction_create.receiver_id).first()
    
    if (payer == None or receiver == None):
        logger.error("Payer or receiver is null")
        raise HTTPException(status_code=400, detail="Payer or Receiver should not be null")
    if (payer.balance < transaction_create.value):
        logger.error(f"Payer {payer.id} has insufficient balance {payer.balance} for transaction value {transaction_create.value}")
        raise HTTPException(status_code=400, detail="Payer has insufficient balance")
    
    update_balance(payer.id, differece=transaction_create.value*-1, db=db)
    update_balance(receiver.id, differece=transaction_create.value, db=db)
    
    now = moment.now().format("YYYY-MM-DD hh:mm:ss")

    db_transaction = models.Transaction(
        value = transaction_create.value,
        description = transaction_create.description,
        date_time = now,
        payer_id = payer.id,
        receiver_id = receiver.id
        )
    
    db.add(db_transaction)
    db.commit()

    date_time = db_transaction.date_time.strftime('%Y/%m/%d %H:%M:%S')

    return schemas.Transaction(
        id=db_transaction.id,
        value=db_transaction.value,
        description=db_transaction.description,
        dateTime=date_time,
        payer=payer,
        receiver=receiver
    )