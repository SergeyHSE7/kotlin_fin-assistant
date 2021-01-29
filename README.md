[![GitHub license](https://img.shields.io/badge/license-Apache%20License%202.0-blue.svg?style=flat)](https://www.apache.org/licenses/LICENSE-2.0)


# FinAssistant

Данный сервис служит для предоставления информации по акциям иностранных и российских компаний. 

Для написания данного сервиса было использовано официальное руководство - [Creating HTTP APIs with Ktor](https://play.kotlinlang.org/hands-on/Creating%20HTTP%20APIs%20with%20Ktor) и 
[OpenAPI от Тинькоффа](https://tinkoffcreditsystems.github.io/invest-openapi/). 

Перед запуском проекта необходимо добавить переменную окружения *api_token* (токен OpenAPI для Sandbox от Тинькоффа).

### Описание предоставляемого API:
- **/api/stocks/{period}/{sortCriteria}/{isDescending}** - возвращает отсортированный список акций с историей цен за указанный период.
- **/api/stocks/{figi}/{period}** - возвращает информацию об одной акции с указанным индентификатором (FIGI - глобальный идентификатор финансового инструмента), а также историю цен за указанный период.

Разработчик: *Елесин Сергей Владимирович* 
