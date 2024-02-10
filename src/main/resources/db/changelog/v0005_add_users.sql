do
$$
begin
for i in 1..20 loop

INSERT INTO public.user (name, date_of_birth, password)
values (CONCAT('testUser', i), '1978-02-05', '$2a$10$ENgOfaDobzBP9lbgIGASee1pw3JVSBdP66ISoNLOygpuNw2/9ReWq');

INSERT INTO public.account (user_id, balance)
values ((select id from public.user where name = CONCAT('testUser', i)), 10 + i * 10);

INSERT INTO public.email_data (user_id, email)
values ((select id from public.user where name = CONCAT('testUser', i)), CONCAT('test1@User', i, '.com'));

INSERT INTO public.email_data (user_id, email)
values ((select id from public.user where name = CONCAT('testUser', i)), CONCAT('test2@User', i, '.com'));

INSERT INTO public.phone_data (user_id, phone)
values ((select id from public.user where name = CONCAT('testUser', i)), 77777777741 + i);

INSERT INTO public.phone_data (user_id, phone)
values ((select id from public.user where name = CONCAT('testUser', i)), 7777777774 + i);

end loop;
end;
$$;
