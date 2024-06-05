修改chatmessage表
 CREATE TABLE ChatMessage (  
     message_id INT PRIMARY KEY AUTO_INCREMENT,  
     groupId INT,  
     userId INT,  
     message_content TEXT, 
 ); 
