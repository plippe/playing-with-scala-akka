# Playing with scala - AKKA

Source code for upcoming blog post

```sh
-> curl localhost:9000/records -s | head -n 5
id,information
25c6b1a1-a883-42c8-858d-00f3c6ab2c50,헰鏋
4b01469d-d2c3-4565-8901-8ec8878ecef6,̕⚊띃栢禣䜆횣ꃖ⺉⌽㬌뱭㬺ꨁ벇ꠊ㘝츇ᴴ蘑挙襁ⲡ䐘
f19a6a35-e0b7-49a3-a823-9fc63496590e,焾ꗲ핆캟࣒貧쇝
abe7be83-3b77-4673-8a22-ef747be00a92,鈲Ք䶂커諰蛩螴꿆

-> curl localhost:9000/requests -H "Content-Type: application/json" -d '{"email": "p.vinchon@gmail.com"}'
{"id":"10e66cbc-ca40-42e2-920c-0b82520d7ed0","email":"p.vinchon@gmail.com"}

-> curl localhost:9000/requests/10e66cbc-ca40-42e2-920c-0b82520d7ed0 -s | head -n 5
id,information
fe7d21b4-7d1c-4634-802f-eaec79b5567e,㗩㴎⛛ᅜꌩ礝袐ꅰ㲸稈举촕⓽넳ꢏ鄠릊儐ꖻ₲簋銣⿙墯⎗뱎明㉏
ff6c1218-93eb-4f83-ab4a-768cf8f1669c,찍䞨᜾᭳醴㜚뒏뇙侑鼎쨴⠛嵫쎙듈룩퍊焑怃湼쬬簲䣶뢛㓶놋타ᵩ䏭劁ㄛ넳꫃摬펟჈㣳䇓떸湉╎묉䳕뢛祍鏖쁯㝋
ab71a483-8152-42fc-bf37-61a2993d6421,쫈뷃㏰
50ad927f-678c-4bf4-b547-2dcd66af650b,䟚틛韺櫟蜝὾ᗌ๨ጉ⏘嗨째塘Ѩ巪ᵇ퉉몎㊆髈
```
