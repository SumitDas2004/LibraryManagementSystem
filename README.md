
# Details of the APIs:

   _This application is not hosted hence domain part in all the URLs is 'example.com' followed by path and query string._


  ## Student Entity:
  
  Properties of a student are as follows:
    
  * id (Generated automatically)
  * name
  * age
  * country
  * phone
  * email
  * created_on (Set automatically)
  * updated_on (Set automatically)
  * Card


        
### Create Request:

    
       URL: example.com/student/create
       Request type: POST
       Body: {
                  "name":"Student Name",
                  "email":"studentEmail@domain.com",
                  "phoneNumber":"1234567890",
                  "age":"19",
                  "country":"India"
              }
              
  ### Read Request:

    
       Request type: GET
       URL: example.com/student/get?phone=1234567890 //Fetches the student details with corresponding phone number
       URL: example.com/student/get?email=studentEmail@domain.com //Fetches the student details with corresponding email
       URL: example.com/student/get?id=1 //Fetches the student details with corresponding student id

  ### Update Request:
  
        Request type: PATCH
        URL: example.com/update/get?phone=1234567890 //In the query string of URL we need to pass query to signify which student's details we want to update 
        URL: example.com/update/get?email=studentEmail@domain.com 
        URL: example.com/update/get?id=1 

        Body: {    // Only need to provide the properties you want to update.
                  "name":"Student Name",
                  "email":"studentEmail@domain.com",
                  "phoneNumber":"1234567890",
                  "age":"19",
                  "country":"India"
                  "cardid":1  //New card's id
              }
  __Delete Request:__
  
         Request type: DELETE
         URL: example.com/delete/get?phone=1234567890 
         URL: example.com/update/get?email=studentEmail@domain.com 
         URL: example.com/update/get?id=1 
           



</br>
</br>

## Card Entity

Properties of a Card are as follows:

* id (Set automatically)
* CardStatus (Can be assigned only two values, ACTIVE or INACTIVE)
* updatedOn (Set automatically)
* cratedOn (Set automatically)
* validThrough (Set automatically)
* Student


### Create Request

      Request type: POST
      URL: example.com/card/issue?student_id=1 //Provide student_id=Student id for whom you want to issue the book
      
### Get Request

      Request type: GET
      URL: example.com/card/get?id=1

### Delete Request

      Request type: DELETE
      URL: example.com/card/delete?id=1


      
</br>
</br>

## Author Entity

Properties of an Author are as follows:

* id (Generated automatically)
* name (Should be unique)
* age
* country
* email

### Create Request

         Request type: POST
         URL: example.com/author/create
         Request Body:{
             "name":"Dennis Ritchie",
             "email":"dennis_ritchie@gmail.com",
             "country":"USA",
             "age":"70"
         }
### Get Request

      Request type: GET
      URL: example.com/author/get?id=1
      URL: example.com/author/get?name=dennis_ritchie   //When provding name as query we need to swap all the spaces with underscores. 
                                                      //For example 'jhon doe' should be passed as 'jhon_doe' 
      URL: example.com/author/get?email=emailId@domain.com

### Update Request

      Request type: PATCH
      URL: example.com/author/update?id=1
      URL: example.com/author/update?name=dennis_ritchie   //When provding name as query we need to swap all the spaces with underscores. 
                                                      //For example 'jhon doe' should be passed as 'jhon_doe' 
      URL: example.com/author/update?email=emailId@domain.com

      Request Body{   //Only need to provide the necessary properties
             "name":"Dennis Ritchie",
             "email":"dennis_ritchie@gmail.com",
             "country":"USA",
             "age":"70"
      }

### Delete Request

         Request type: DELETE
         URL: example.com/author/delete?id=1
         URL: example.com/author/delete?name=dennis_ritchie   //When provding name as query we need to swap all the spaces with underscores. 
                                                         //For example 'jhon doe' should be passed as 'jhon_doe' 
         URL: example.com/author/delete?email=emailId@domain.com



</br>
</br>


## Book Entity

Properties of Book entity are as follows:

* id
* name
* author
* number of pages
* available
* published date
* language
* isbn
* genre

### Create Request
         Request type: POST
         URL: example.com/book/create
         Request Body:{
             "name":"Programming in C",
             "authorName":"Dennis Ritchie",
             "numberOfPages":"300",
             "language":"english",
             "available":"true",
             "isbn":"123456789",
             "publishedDate":"1978-02-22T00:00:00",
             "genre":"Programming"
         }

### Get Request

         Request type: GET
         URL: example.com/book/get?id=1
         URL: example.com/book/get?isbn=123456789
         URL: example.com/book/get?name=programming_in_c&author=dennis_ritchie      //When provding name and author as query we need to swap all the spaces with underscores. 
                                                         //For example 'jhon doe' should be passed as 'jhon_doe' 

### Update Request

         Request type: PATCH
         URL: example.com/book/update?id=1
         URL: example.com/book/update?isbn=123456789
         URL: example.com/book/update?name=programming_in_c&author=dennis_ritchie
         
         Request Body:{//Just provide the necessary details you want to update
             "name":"Programming in C",
             "authorName":"Dennis Ritchie",
             "numberOfPages":"300",
             "language":"english",
             "available":"true",
             "isbn":"123456789",
             "publishedDate":"1978-02-22T00:00:00",
             "genre":"Programming"
         }

### Delete Request
         Request type: DELETE
         URL: example.com/book/delete?id=1
         URL: example.com/book/delete?isbn=123456789
         URL: example.com/book/delete?name=programming_in_c&author=dennis_ritchie


</br>

## Transcation Entity

   Properties of Transaction entity are as follows:

   * id
   * card
   * book
   * status
   * isIssued
   * isRetured
   * fineAmount
   * createdOn
   * updatedOn

### Issue Request

      Request type: POST
      URL: example.com/transaction/issue
      Body: {
          "bookId":"1",
          "cardId":"1"
      }

### Return Request
      

      Request type: POST
      URL: example.com/transaction/return 
      Body: {
          "bookId":"1",
          "cardId":"1"
      }

### Get Request
      Request type: GET
      URL: example.com/transaction/get?id=1


### Get transaction by book Request

Used to fetch all the transactions involving a particular book

      Request type: GET
      URL: example.com/transaction/getbybook?id=1 //provide the book id
