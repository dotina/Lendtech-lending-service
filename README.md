# Lendtech-lending-service

Create a Java/Clojure application that simulates a Lending/Repayment API.
The Developed API should have the following requirements met:
1. Receive a Lending request from a user (Loans Should be tied to a subscriber
   MSISDN)
2. Receive repayment requests from the user should they top up their loans and update
   the relevant tables
3. Add logic to sweep/clear old defaulted loans (The decision to clear the loans should
   be configurable as that may vary from market to market. ie : Should the loan be cleared
   after a loan age of 6 months?)
4. Simulate generation of dumps from the database to an SFTP server.
5. As you’re designing the API make decisions such as whether a user can have one or
   multiple loans. Your database should cater for that.
6. Once a subscriber takes a loan or makes a repayment they should be notified by
   SMS of the amount lent if it's a lending operation or the amount recovered if full or
   partial.
7. All the endpoints and logic should be tested using a testing library of your choice.
8. Ensure that the endpoints are documented on an openly available documenting
   library such as Swagger or any other flavor of OpenAPI
