
API endpoint names are:
```
/list
/list/{filter}/{param}   where filter is {author | category | language | isbn | name | taken | available}
/add?{name} & {author} & {category} & {language} & {pubDate} & {isbn} & {guid}
/getBook/{guid}
/delete/{guid}
/take?{name} & {GUID} & {period}
```
