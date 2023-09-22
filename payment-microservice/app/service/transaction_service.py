from sqlalchemy.orm import Session
import moment

from .participant_service import update_balance
from ..dto import schemas
from ..database import models

def create_transaction(transaction_create: schemas.TransactionCreate, db: Session):
    payer = db.query(models.Participant).filter(models.Participant.id == transaction_create.payer_id).first()
    receiver = db.query(models.Participant).filter(models.Participant.id == transaction_create.receiver_id).first()
    
    if (payer == None or receiver == None):
        raise BaseException("Payer or Receiver should not be null")
    if (payer.balance < transaction_create.value):
        raise BaseException("Payer has insufficient balance")
    
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