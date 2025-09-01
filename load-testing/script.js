import http from "k6/http";
import { check, sleep } from "k6";

// нет нагрузки
// iteration_duration.............: avg=4.99ms min=2.95ms med=4.88ms max=19.78ms p(90)=6.06ms p(95)=6.44ms
// iterations.....................: 999    199.779195/s

// iteration_duration.............: avg=4.78ms min=2.66ms med=4.59ms max=15.41ms p(90)=5.96ms p(95)=6.55ms
// iterations.....................: 1043   208.469636/s

// 2 vu
// iteration_duration.............: avg=5.41ms min=2.55ms med=5.29ms max=22.36ms p(90)=6.52ms p(95)=7ms
// iterations.....................: 1845   368.636127/s

// 3 vu
// iteration_duration.............: avg=8.23ms min=3.4ms med=8.03ms max=23.37ms p(90)=11.04ms p(95)=12.06ms
// iterations.....................: 1818   363.270521/s

// 10 vu
// iteration_duration.............: avg=27.73ms min=5.42ms med=8.76ms max=5.02s p(90)=20.05ms p(95)=35.97ms
// iterations.....................: 1807   359.411691/s

// 2 vu
// http_req_duration..............: avg=3.3ms  min=1.53ms med=3.26ms max=15.9ms  p(90)=4.02ms p(95)=4.36ms
// { expected_response:true }...: avg=3.3ms  min=1.53ms med=3.26ms max=15.9ms  p(90)=4.02ms p(95)=4.36ms
// http_req_failed................: 0.00%  0 out of 2917
// http_reqs......................: 2917   583.090647/s

// 3 vu
// http_req_duration..............: avg=4.84ms min=1.81ms med=4.82ms max=17.94ms p(90)=6.07ms p(95)=6.66ms
// { expected_response:true }...: avg=4.84ms min=1.81ms med=4.82ms max=17.94ms p(90)=6.07ms p(95)=6.66ms
// http_req_failed................: 0.00%  0 out of 3020
// http_reqs......................: 3020   603.61639/s


export let options = {
    vus: 3,
    duration: "5s",
};

export default function () {
    // const url = "http://localhost:8080/api/v1/wallets/bce99cfe-2e42-4813-80dd-5c1cb41b7b4a";
    const url = "http://localhost:8080/api/v1/wallet";

    // // Пример тела запроса
    const payload = JSON.stringify({
        id: "bce99cfe-2e42-4813-80dd-5c1cb41b7b4a",
        amount: "1",
        operationType: "WITHDRAW"
    });

    const params = {
        headers: {
            "Content-Type": "application/json",
        },
    };

    // let res = http.get(url);
    let res = http.post(url, payload, params);

    // Проверки
    check(res, {
        "статус 200 или 201": (r) => r.status === 200
    });

    // sleep(0.001);
}
