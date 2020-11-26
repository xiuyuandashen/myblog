let xmlHttpRequest = new XMLHttpRequest();


function Ajax1(url,data,requestLeixing,func)
{
    xmlHttpRequest.open(requestLeixing,url,true);
    xmlHttpRequest.setRequestHeader("Content-type", "application/json");
    xmlHttpRequest.send(JSON.stringify(data));
    xmlHttpRequest.onreadystatechange=function () {
        if (xmlHttpRequest.status == 200 && xmlHttpRequest.readyState == 4) {
            func(xmlHttpRequest);
        }
    };
}

function Ajax2(url,requestLeixing,func)
{
    xmlHttpRequest.open(requestLeixing,url,true);
    xmlHttpRequest.setRequestHeader("Content-type", "application/json");
    xmlHttpRequest.send();
    xmlHttpRequest.onreadystatechange=function () {
        if (xmlHttpRequest.status == 200 && xmlHttpRequest.readyState == 4) {
            func(xmlHttpRequest);
        }
    };
}