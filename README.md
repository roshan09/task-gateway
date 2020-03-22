##Introduction to task-gateway?
    Basically an entry point which transform event of one world to another. 
    This gateway will accespt an event and produce multiple task events based 
    on given configuration.
    Configurations can be of following type :
        1) Map source field to destinition field.
        2) Get other details based on sourceField by fetching the data from API.
##Example :     
```json
    input-event.json:
    {
      "eventType": "ORDER_PLACED",
      "customerDeatils" : {
        "customerId": "cust123",
        "customerName" : "John",
        "email" : "John@gmail.com",
        "mobileNumber" : "9988776655"      
      },
      "orderDeatils" : {
        "orderId" : "111",
        "orderedItem"  : "Sony Headphones"      
      }
    }    
```

####Create following subTasks once the order is placed

```json
    sms-notification.json:
    {
      "eventType": "ORDER_PLACED_SMS_NOTIFICATION",
      "customerName" : "John",
      "orderedItem"  : "Sony Headphones",
      "mobileNumber" : "9988776655"
    }    
```

```json
    email-notification.json:
    {
      "eventType": "ORDER_PLACED_EMAIL_NOTIFICATION",
      "customerName" : "John",
      "orderedItem"  : "Sony Headphones",
      "mobileNumber" : "9988776655"
    }
``` 
    Publish DISPATCH_ORDER event and fetch the necessary address deatils
    based on orderId from another service
```json
    dispatch-order.json:
    {
      "eventType": "DISPATCH_ORDER",
      "customerId": "cust123",
      "orderId" : "111",
      "orderedItem"  : "Sony Headphones",
      "addressDeatils" : {
        "landmark"  : "rto office",
        "city"  : "pune"
        "addressLine1"  : "A212 Hermes"
      }
    }
```
  
        
    
        
   
        
        