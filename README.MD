## Spring boot + MyBatis 3 + MySQL ##

각 내용은 다음과 같이 구성되었다.

### 개발 환경 ### 
    Programming Language - Java 1.7
    IDE - Eclipse
    DB - MySQL
    Framework - MyBatis, Spring boot
    Build Tool - Maven

### 설치 ###

    CREATE TABLE USERS (
      username varchar_ignorecase(50) not null primary key,
      password varchar_ignorecase(50) not null,
      enabled boolean not null
    ) ;

	CREATE TABLE authorities (
	    username varchar_ignorecase(50) not null,
	    authority varchar_ignorecase(50) not null,
		 constraint fk_authorities_users foreign key(username) references users(username)
	) ;
    
	create unique index ix_auth_username on authorities (username,authority);
	   
