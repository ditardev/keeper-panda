SET search_path TO panda;

insert into users(uuid)
values ('8a8ac7b4-6e1f-4677-ba83-e4acb8559a7b'), ('bf5b1024-ca51-4744-9e61-0d2177ca4b80');

insert into mails(mail)
values ('test_mail');

insert into accounts(user_id, name, account, password, link, description, mail, type, updated)
values (1,'steam', 'Apostality', 'temp_pass', 'some_link', 'some_descr', 1, 'GAMES', current_timestamp);


