  create table profile(
      username varchar(200) NOT NULL primary key,
      firstname varchar(200) NOT NULL,
      lastname varchar(200) NOT NULL
  );

  create unique index pk_unique_firstname_lastname_combo on profile(firstname,lastname);

  insert into profile(username, firstname, lastname) values('admin', 'Admin', 'Admin');
  insert into profile(username, firstname, lastname) values('user', 'User', 'User');