!function(){function e(t){return e="function"==typeof Symbol&&"symbol"==typeof Symbol.iterator?function(e){return typeof e}:function(e){return e&&"function"==typeof Symbol&&e.constructor===Symbol&&e!==Symbol.prototype?"symbol":typeof e},e(t)}System.register(["./index-legacy.a832f31f.js"],(function(t){"use strict";var n;return{setters:[function(e){n=e.e}],execute:function(){t({a:function(e,t,n,r,o){var i="";null!=t&&""!=t&&(i+="&deviceKey="+t);null!=n&&""!=n&&(i+="&beginDate="+n);null!=r&&""!=r&&(i+="&endDate="+r);null!=o&&""!=o&&(i+="&deviceType="+o);return Be({url:"/api/analysis?limit="+e+i,method:"get",params:{}})},b:function(e,t){return Be({url:"/api/lastData?deviceKey="+e+"&pileId="+t,method:"get",params:{}})},c:function(e,t,n){return Be({url:"/api/historyBacks?page="+e+"&size="+t+"&deviceKey="+n,method:"get",params:{}})},d:function(e,t,n){return Be({url:"/api/datas?deviceKey="+e+"&pileId="+t+"&fields="+n,method:"get",params:{}})},e:function(e,t){var n="/api/editConfig?deviceId="+t;return Be.post(n,e)},f:function(){return Be({url:"/api/deviceGroups",method:"get",params:{}})},g:function(e,t,n,r,o,i,s,a,u,c,f,l,p,d,h){var m="";o=o.replace(/\+/g,"%2B"),null!=i&&""!=i&&(m+="&startTime="+i/1e3);null!=s&&""!=s&&(m+="&endTime="+s/1e3);var g="";null!=a&&""!=a&&(g+="&minDepth="+a);null!=f&&""!=f&&(g+="&maxDepth="+f);null!=l&&""!=l&&(g+="&minAvgAsh="+l);null!=p&&""!=p&&(g+="&maxAvgAsh="+p);null!=u&&""!=u&&(g+="&minAsh="+u);null!=d&&""!=d&&(g+="&maxAsh="+d);null!=c&&""!=c&&(g+="&minPileTime="+c);null!=h&&""!=h&&(g+="&maxPileTime="+h);return Be({url:"/api/historys?deviceType="+e+"&page="+t+"&deviceKey="+n+"&size="+r+"&pileDescribe="+o+m+g,method:"get",params:{}})},h:function(){return Be({url:"/api/groupSum",method:"get",params:{}})},i:function(e,t,n,r,o){return o=o.replace(/\+/g,"%2B"),Be({url:"/api/historyWarns?deviceType="+e+"&page="+t+"&deviceKey="+n+"&size="+r+"&pileDescribe="+o,method:"get",params:{}})},j:function(e,t,n,r){""===n?n=0:n/=1e3;""===r?r=9999999999:r/=1e3;return Be({url:"/api/historyAnalysis?deviceKey="+e+"&deviceType="+t+"&beginDate="+n+"&endDate="+r,methods:"get",params:{}})},l:function(e,t){return Be({url:"/api/login?userName="+e+"&password="+t,method:"post",params:{}})},n:function(){return Be({url:"/api/newest",method:"get",params:{}})},p:function(e){return Be({url:"/api/pileIds?deviceKey="+e,method:"get",params:{}})},q:function(e,t,n){return Be({url:"/api/devices?size="+e+"&current="+t+"&deviceType="+n,method:"get",params:{}})}});var r={exports:{}},o=function(e,t){return function(){for(var n=new Array(arguments.length),r=0;r<n.length;r++)n[r]=arguments[r];return e.apply(t,n)}},i=o,s=Object.prototype.toString;function a(e){return"[object Array]"===s.call(e)}function u(e){return void 0===e}function c(t){return null!==t&&"object"===e(t)}function f(e){if("[object Object]"!==s.call(e))return!1;var t=Object.getPrototypeOf(e);return null===t||t===Object.prototype}function l(e){return"[object Function]"===s.call(e)}function p(t,n){if(null!=t)if("object"!==e(t)&&(t=[t]),a(t))for(var r=0,o=t.length;r<o;r++)n.call(null,t[r],r,t);else for(var i in t)Object.prototype.hasOwnProperty.call(t,i)&&n.call(null,t[i],i,t)}var d={isArray:a,isArrayBuffer:function(e){return"[object ArrayBuffer]"===s.call(e)},isBuffer:function(e){return null!==e&&!u(e)&&null!==e.constructor&&!u(e.constructor)&&"function"==typeof e.constructor.isBuffer&&e.constructor.isBuffer(e)},isFormData:function(e){return"undefined"!=typeof FormData&&e instanceof FormData},isArrayBufferView:function(e){return"undefined"!=typeof ArrayBuffer&&ArrayBuffer.isView?ArrayBuffer.isView(e):e&&e.buffer&&e.buffer instanceof ArrayBuffer},isString:function(e){return"string"==typeof e},isNumber:function(e){return"number"==typeof e},isObject:c,isPlainObject:f,isUndefined:u,isDate:function(e){return"[object Date]"===s.call(e)},isFile:function(e){return"[object File]"===s.call(e)},isBlob:function(e){return"[object Blob]"===s.call(e)},isFunction:l,isStream:function(e){return c(e)&&l(e.pipe)},isURLSearchParams:function(e){return"undefined"!=typeof URLSearchParams&&e instanceof URLSearchParams},isStandardBrowserEnv:function(){return("undefined"==typeof navigator||"ReactNative"!==navigator.product&&"NativeScript"!==navigator.product&&"NS"!==navigator.product)&&("undefined"!=typeof window&&"undefined"!=typeof document)},forEach:p,merge:function e(){var t={};function n(n,r){f(t[r])&&f(n)?t[r]=e(t[r],n):f(n)?t[r]=e({},n):a(n)?t[r]=n.slice():t[r]=n}for(var r=0,o=arguments.length;r<o;r++)p(arguments[r],n);return t},extend:function(e,t,n){return p(t,(function(t,r){e[r]=n&&"function"==typeof t?i(t,n):t})),e},trim:function(e){return e.trim?e.trim():e.replace(/^\s+|\s+$/g,"")},stripBOM:function(e){return 65279===e.charCodeAt(0)&&(e=e.slice(1)),e}},h=d;function m(e){return encodeURIComponent(e).replace(/%3A/gi,":").replace(/%24/g,"$").replace(/%2C/gi,",").replace(/%20/g,"+").replace(/%5B/gi,"[").replace(/%5D/gi,"]")}var g=function(e,t,n){if(!t)return e;var r;if(n)r=n(t);else if(h.isURLSearchParams(t))r=t.toString();else{var o=[];h.forEach(t,(function(e,t){null!=e&&(h.isArray(e)?t+="[]":e=[e],h.forEach(e,(function(e){h.isDate(e)?e=e.toISOString():h.isObject(e)&&(e=JSON.stringify(e)),o.push(m(t)+"="+m(e))})))})),r=o.join("&")}if(r){var i=e.indexOf("#");-1!==i&&(e=e.slice(0,i)),e+=(-1===e.indexOf("?")?"?":"&")+r}return e},y=d;function v(){this.handlers=[]}v.prototype.use=function(e,t,n){return this.handlers.push({fulfilled:e,rejected:t,synchronous:!!n&&n.synchronous,runWhen:n?n.runWhen:null}),this.handlers.length-1},v.prototype.eject=function(e){this.handlers[e]&&(this.handlers[e]=null)},v.prototype.forEach=function(e){y.forEach(this.handlers,(function(t){null!==t&&e(t)}))};var b=v,w=d,S=function(e,t,n,r,o){return e.config=t,n&&(e.code=n),e.request=r,e.response=o,e.isAxiosError=!0,e.toJSON=function(){return{message:this.message,name:this.name,description:this.description,number:this.number,fileName:this.fileName,lineNumber:this.lineNumber,columnNumber:this.columnNumber,stack:this.stack,config:this.config,code:this.code,status:this.response&&this.response.status?this.response.status:null}},e},E=S,x=function(e,t,n,r,o){var i=new Error(e);return E(i,t,n,r,o)},j=x,T=d,O=T.isStandardBrowserEnv()?{write:function(e,t,n,r,o,i){var s=[];s.push(e+"="+encodeURIComponent(t)),T.isNumber(n)&&s.push("expires="+new Date(n).toGMTString()),T.isString(r)&&s.push("path="+r),T.isString(o)&&s.push("domain="+o),!0===i&&s.push("secure"),document.cookie=s.join("; ")},read:function(e){var t=document.cookie.match(new RegExp("(^|;\\s*)("+e+")=([^;]*)"));return t?decodeURIComponent(t[3]):null},remove:function(e){this.write(e,"",Date.now()-864e5)}}:{write:function(){},read:function(){return null},remove:function(){}},A=function(e){return/^([a-z][a-z\d\+\-\.]*:)?\/\//i.test(e)},C=function(e,t){return t?e.replace(/\/+$/,"")+"/"+t.replace(/^\/+/,""):e},N=d,R=["age","authorization","content-length","content-type","etag","expires","from","host","if-modified-since","if-unmodified-since","last-modified","location","max-forwards","proxy-authorization","referer","retry-after","user-agent"],P=d,U=P.isStandardBrowserEnv()?function(){var e,t=/(msie|trident)/i.test(navigator.userAgent),n=document.createElement("a");function r(e){var r=e;return t&&(n.setAttribute("href",r),r=n.href),n.setAttribute("href",r),{href:n.href,protocol:n.protocol?n.protocol.replace(/:$/,""):"",host:n.host,search:n.search?n.search.replace(/^\?/,""):"",hash:n.hash?n.hash.replace(/^#/,""):"",hostname:n.hostname,port:n.port,pathname:"/"===n.pathname.charAt(0)?n.pathname:"/"+n.pathname}}return e=r(window.location.href),function(t){var n=P.isString(t)?r(t):t;return n.protocol===e.protocol&&n.host===e.host}}():function(){return!0};function B(e){this.message=e}B.prototype.toString=function(){return"Cancel"+(this.message?": "+this.message:"")},B.prototype.__CANCEL__=!0;var k=B,D=d,L=function(e,t,n){var r=n.config.validateStatus;n.status&&r&&!r(n.status)?t(j("Request failed with status code "+n.status,n.config,null,n.request,n)):e(n)},q=O,_=g,I=function(e,t){return e&&!A(t)?C(e,t):t},z=function(e){var t,n,r,o={};return e?(N.forEach(e.split("\n"),(function(e){if(r=e.indexOf(":"),t=N.trim(e.substr(0,r)).toLowerCase(),n=N.trim(e.substr(r+1)),t){if(o[t]&&R.indexOf(t)>=0)return;o[t]="set-cookie"===t?(o[t]?o[t]:[]).concat([n]):o[t]?o[t]+", "+n:n}})),o):o},F=U,J=x,K=Z,H=k,M=function(e){return new Promise((function(t,n){var r,o=e.data,i=e.headers,s=e.responseType;function a(){e.cancelToken&&e.cancelToken.unsubscribe(r),e.signal&&e.signal.removeEventListener("abort",r)}D.isFormData(o)&&delete i["Content-Type"];var u=new XMLHttpRequest;if(e.auth){var c=e.auth.username||"",f=e.auth.password?unescape(encodeURIComponent(e.auth.password)):"";i.Authorization="Basic "+btoa(c+":"+f)}var l=I(e.baseURL,e.url);function p(){if(u){var r="getAllResponseHeaders"in u?z(u.getAllResponseHeaders()):null,o={data:s&&"text"!==s&&"json"!==s?u.response:u.responseText,status:u.status,statusText:u.statusText,headers:r,config:e,request:u};L((function(e){t(e),a()}),(function(e){n(e),a()}),o),u=null}}if(u.open(e.method.toUpperCase(),_(l,e.params,e.paramsSerializer),!0),u.timeout=e.timeout,"onloadend"in u?u.onloadend=p:u.onreadystatechange=function(){u&&4===u.readyState&&(0!==u.status||u.responseURL&&0===u.responseURL.indexOf("file:"))&&setTimeout(p)},u.onabort=function(){u&&(n(J("Request aborted",e,"ECONNABORTED",u)),u=null)},u.onerror=function(){n(J("Network Error",e,null,u)),u=null},u.ontimeout=function(){var t=e.timeout?"timeout of "+e.timeout+"ms exceeded":"timeout exceeded",r=e.transitional||K.transitional;e.timeoutErrorMessage&&(t=e.timeoutErrorMessage),n(J(t,e,r.clarifyTimeoutError?"ETIMEDOUT":"ECONNABORTED",u)),u=null},D.isStandardBrowserEnv()){var d=(e.withCredentials||F(l))&&e.xsrfCookieName?q.read(e.xsrfCookieName):void 0;d&&(i[e.xsrfHeaderName]=d)}"setRequestHeader"in u&&D.forEach(i,(function(e,t){void 0===o&&"content-type"===t.toLowerCase()?delete i[t]:u.setRequestHeader(t,e)})),D.isUndefined(e.withCredentials)||(u.withCredentials=!!e.withCredentials),s&&"json"!==s&&(u.responseType=e.responseType),"function"==typeof e.onDownloadProgress&&u.addEventListener("progress",e.onDownloadProgress),"function"==typeof e.onUploadProgress&&u.upload&&u.upload.addEventListener("progress",e.onUploadProgress),(e.cancelToken||e.signal)&&(r=function(e){u&&(n(!e||e&&e.type?new H("canceled"):e),u.abort(),u=null)},e.cancelToken&&e.cancelToken.subscribe(r),e.signal&&(e.signal.aborted?r():e.signal.addEventListener("abort",r))),o||(o=null),u.send(o)}))},V=d,W=function(e,t){w.forEach(e,(function(n,r){r!==t&&r.toUpperCase()===t.toUpperCase()&&(e[t]=n,delete e[r])}))},X=S,$={"Content-Type":"application/x-www-form-urlencoded"};function G(e,t){!V.isUndefined(e)&&V.isUndefined(e["Content-Type"])&&(e["Content-Type"]=t)}var Q,Y={transitional:{silentJSONParsing:!0,forcedJSONParsing:!0,clarifyTimeoutError:!1},adapter:(("undefined"!=typeof XMLHttpRequest||"undefined"!=typeof process&&"[object process]"===Object.prototype.toString.call(process))&&(Q=M),Q),transformRequest:[function(e,t){return W(t,"Accept"),W(t,"Content-Type"),V.isFormData(e)||V.isArrayBuffer(e)||V.isBuffer(e)||V.isStream(e)||V.isFile(e)||V.isBlob(e)?e:V.isArrayBufferView(e)?e.buffer:V.isURLSearchParams(e)?(G(t,"application/x-www-form-urlencoded;charset=utf-8"),e.toString()):V.isObject(e)||t&&"application/json"===t["Content-Type"]?(G(t,"application/json"),function(e,t,n){if(V.isString(e))try{return(t||JSON.parse)(e),V.trim(e)}catch(r){if("SyntaxError"!==r.name)throw r}return(n||JSON.stringify)(e)}(e)):e}],transformResponse:[function(e){var t=this.transitional||Y.transitional,n=t&&t.silentJSONParsing,r=t&&t.forcedJSONParsing,o=!n&&"json"===this.responseType;if(o||r&&V.isString(e)&&e.length)try{return JSON.parse(e)}catch(i){if(o){if("SyntaxError"===i.name)throw X(i,this,"E_JSON_PARSE");throw i}}return e}],timeout:0,xsrfCookieName:"XSRF-TOKEN",xsrfHeaderName:"X-XSRF-TOKEN",maxContentLength:-1,maxBodyLength:-1,validateStatus:function(e){return e>=200&&e<300},headers:{common:{Accept:"application/json, text/plain, */*"}}};V.forEach(["delete","get","head"],(function(e){Y.headers[e]={}})),V.forEach(["post","put","patch"],(function(e){Y.headers[e]=V.merge($)}));var Z=Y,ee=d,te=Z,ne=function(e){return!(!e||!e.__CANCEL__)},re=d,oe=function(e,t,n){var r=this||te;return ee.forEach(n,(function(n){e=n.call(r,e,t)})),e},ie=ne,se=Z,ae=k;function ue(e){if(e.cancelToken&&e.cancelToken.throwIfRequested(),e.signal&&e.signal.aborted)throw new ae("canceled")}var ce=d,fe=function(e,t){t=t||{};var n={};function r(e,t){return ce.isPlainObject(e)&&ce.isPlainObject(t)?ce.merge(e,t):ce.isPlainObject(t)?ce.merge({},t):ce.isArray(t)?t.slice():t}function o(n){return ce.isUndefined(t[n])?ce.isUndefined(e[n])?void 0:r(void 0,e[n]):r(e[n],t[n])}function i(e){if(!ce.isUndefined(t[e]))return r(void 0,t[e])}function s(n){return ce.isUndefined(t[n])?ce.isUndefined(e[n])?void 0:r(void 0,e[n]):r(void 0,t[n])}function a(n){return n in t?r(e[n],t[n]):n in e?r(void 0,e[n]):void 0}var u={url:i,method:i,data:i,baseURL:s,transformRequest:s,transformResponse:s,paramsSerializer:s,timeout:s,timeoutMessage:s,withCredentials:s,adapter:s,responseType:s,xsrfCookieName:s,xsrfHeaderName:s,onUploadProgress:s,onDownloadProgress:s,decompress:s,maxContentLength:s,maxBodyLength:s,transport:s,httpAgent:s,httpsAgent:s,cancelToken:s,socketPath:s,responseEncoding:s,validateStatus:a};return ce.forEach(Object.keys(e).concat(Object.keys(t)),(function(e){var t=u[e]||o,r=t(e);ce.isUndefined(r)&&t!==a||(n[e]=r)})),n},le="0.24.0",pe=le,de={};["object","boolean","number","function","string","symbol"].forEach((function(t,n){de[t]=function(r){return e(r)===t||"a"+(n<1?"n ":" ")+t}}));var he={};de.transitional=function(e,t,n){function r(e,t){return"[Axios v"+pe+"] Transitional option '"+e+"'"+t+(n?". "+n:"")}return function(n,o,i){if(!1===e)throw new Error(r(o," has been removed"+(t?" in "+t:"")));return t&&!he[o]&&(he[o]=!0,console.warn(r(o," has been deprecated since v"+t+" and will be removed in the near future"))),!e||e(n,o,i)}};var me={assertOptions:function(t,n,r){if("object"!==e(t))throw new TypeError("options must be an object");for(var o=Object.keys(t),i=o.length;i-- >0;){var s=o[i],a=n[s];if(a){var u=t[s],c=void 0===u||a(u,s,t);if(!0!==c)throw new TypeError("option "+s+" must be "+c)}else if(!0!==r)throw Error("Unknown option "+s)}},validators:de},ge=d,ye=g,ve=b,be=function(e){return ue(e),e.headers=e.headers||{},e.data=oe.call(e,e.data,e.headers,e.transformRequest),e.headers=re.merge(e.headers.common||{},e.headers[e.method]||{},e.headers),re.forEach(["delete","get","head","post","put","patch","common"],(function(t){delete e.headers[t]})),(e.adapter||se.adapter)(e).then((function(t){return ue(e),t.data=oe.call(e,t.data,t.headers,e.transformResponse),t}),(function(t){return ie(t)||(ue(e),t&&t.response&&(t.response.data=oe.call(e,t.response.data,t.response.headers,e.transformResponse))),Promise.reject(t)}))},we=fe,Se=me,Ee=Se.validators;function xe(e){this.defaults=e,this.interceptors={request:new ve,response:new ve}}xe.prototype.request=function(e){"string"==typeof e?(e=arguments[1]||{}).url=arguments[0]:e=e||{},(e=we(this.defaults,e)).method?e.method=e.method.toLowerCase():this.defaults.method?e.method=this.defaults.method.toLowerCase():e.method="get";var t=e.transitional;void 0!==t&&Se.assertOptions(t,{silentJSONParsing:Ee.transitional(Ee.boolean),forcedJSONParsing:Ee.transitional(Ee.boolean),clarifyTimeoutError:Ee.transitional(Ee.boolean)},!1);var n=[],r=!0;this.interceptors.request.forEach((function(t){"function"==typeof t.runWhen&&!1===t.runWhen(e)||(r=r&&t.synchronous,n.unshift(t.fulfilled,t.rejected))}));var o,i=[];if(this.interceptors.response.forEach((function(e){i.push(e.fulfilled,e.rejected)})),!r){var s=[be,void 0];for(Array.prototype.unshift.apply(s,n),s=s.concat(i),o=Promise.resolve(e);s.length;)o=o.then(s.shift(),s.shift());return o}for(var a=e;n.length;){var u=n.shift(),c=n.shift();try{a=u(a)}catch(f){c(f);break}}try{o=be(a)}catch(f){return Promise.reject(f)}for(;i.length;)o=o.then(i.shift(),i.shift());return o},xe.prototype.getUri=function(e){return e=we(this.defaults,e),ye(e.url,e.params,e.paramsSerializer).replace(/^\?/,"")},ge.forEach(["delete","get","head","options"],(function(e){xe.prototype[e]=function(t,n){return this.request(we(n||{},{method:e,url:t,data:(n||{}).data}))}})),ge.forEach(["post","put","patch"],(function(e){xe.prototype[e]=function(t,n,r){return this.request(we(r||{},{method:e,url:t,data:n}))}}));var je=xe,Te=k;function Oe(e){if("function"!=typeof e)throw new TypeError("executor must be a function.");var t;this.promise=new Promise((function(e){t=e}));var n=this;this.promise.then((function(e){if(n._listeners){var t,r=n._listeners.length;for(t=0;t<r;t++)n._listeners[t](e);n._listeners=null}})),this.promise.then=function(e){var t,r=new Promise((function(e){n.subscribe(e),t=e})).then(e);return r.cancel=function(){n.unsubscribe(t)},r},e((function(e){n.reason||(n.reason=new Te(e),t(n.reason))}))}Oe.prototype.throwIfRequested=function(){if(this.reason)throw this.reason},Oe.prototype.subscribe=function(e){this.reason?e(this.reason):this._listeners?this._listeners.push(e):this._listeners=[e]},Oe.prototype.unsubscribe=function(e){if(this._listeners){var t=this._listeners.indexOf(e);-1!==t&&this._listeners.splice(t,1)}},Oe.source=function(){var e;return{token:new Oe((function(t){e=t})),cancel:e}};var Ae=Oe,Ce=d,Ne=o,Re=je,Pe=fe;var Ue=function e(t){var n=new Re(t),r=Ne(Re.prototype.request,n);return Ce.extend(r,Re.prototype,n),Ce.extend(r,n),r.create=function(n){return e(Pe(t,n))},r}(Z);Ue.Axios=Re,Ue.Cancel=k,Ue.CancelToken=Ae,Ue.isCancel=ne,Ue.VERSION=le,Ue.all=function(e){return Promise.all(e)},Ue.spread=function(e){return function(t){return e.apply(null,t)}},Ue.isAxiosError=function(t){return"object"===e(t)&&!0===t.isAxiosError},r.exports=Ue,r.exports.default=Ue;var Be=r.exports.create({baseURL:"",timeout:6e4});Be.interceptors.request.use((function(e){return e.headers.token=sessionStorage.token,"post"==e.method&&(e.headers["Content-Type"]="application/json"),e}),(function(e){return console.log(e),Promise.reject(e)})),Be.interceptors.response.use((function(e){return e.data}),(function(e){return console.log("err"+e),401==e.response.status&&(sessionStorage.clear(),globalThis.$router.push("/login")),n.exports.Message({message:e.message,type:"error",duration:5e3}),Promise.reject(e)}))}}}))}();
