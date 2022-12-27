import{e as Ee}from"./index.0cb1b95f.js";var K={exports:{}},oe=function(e,t){return function(){for(var n=new Array(arguments.length),a=0;a<n.length;a++)n[a]=arguments[a];return e.apply(t,n)}},Se=oe,g=Object.prototype.toString;function V(r){return g.call(r)==="[object Array]"}function J(r){return typeof r=="undefined"}function ge(r){return r!==null&&!J(r)&&r.constructor!==null&&!J(r.constructor)&&typeof r.constructor.isBuffer=="function"&&r.constructor.isBuffer(r)}function Ce(r){return g.call(r)==="[object ArrayBuffer]"}function Re(r){return typeof FormData!="undefined"&&r instanceof FormData}function Oe(r){var e;return typeof ArrayBuffer!="undefined"&&ArrayBuffer.isView?e=ArrayBuffer.isView(r):e=r&&r.buffer&&r.buffer instanceof ArrayBuffer,e}function xe(r){return typeof r=="string"}function Ne(r){return typeof r=="number"}function ue(r){return r!==null&&typeof r=="object"}function P(r){if(g.call(r)!=="[object Object]")return!1;var e=Object.getPrototypeOf(r);return e===null||e===Object.prototype}function $e(r){return g.call(r)==="[object Date]"}function Ae(r){return g.call(r)==="[object File]"}function Te(r){return g.call(r)==="[object Blob]"}function fe(r){return g.call(r)==="[object Function]"}function Ue(r){return ue(r)&&fe(r.pipe)}function Pe(r){return typeof URLSearchParams!="undefined"&&r instanceof URLSearchParams}function Be(r){return r.trim?r.trim():r.replace(/^\s+|\s+$/g,"")}function Le(){return typeof navigator!="undefined"&&(navigator.product==="ReactNative"||navigator.product==="NativeScript"||navigator.product==="NS")?!1:typeof window!="undefined"&&typeof document!="undefined"}function W(r,e){if(!(r===null||typeof r=="undefined"))if(typeof r!="object"&&(r=[r]),V(r))for(var t=0,s=r.length;t<s;t++)e.call(null,r[t],t,r);else for(var n in r)Object.prototype.hasOwnProperty.call(r,n)&&e.call(null,r[n],n,r)}function z(){var r={};function e(n,a){P(r[a])&&P(n)?r[a]=z(r[a],n):P(n)?r[a]=z({},n):V(n)?r[a]=n.slice():r[a]=n}for(var t=0,s=arguments.length;t<s;t++)W(arguments[t],e);return r}function je(r,e,t){return W(e,function(n,a){t&&typeof n=="function"?r[a]=Se(n,t):r[a]=n}),r}function qe(r){return r.charCodeAt(0)===65279&&(r=r.slice(1)),r}var b={isArray:V,isArrayBuffer:Ce,isBuffer:ge,isFormData:Re,isArrayBufferView:Oe,isString:xe,isNumber:Ne,isObject:ue,isPlainObject:P,isUndefined:J,isDate:$e,isFile:Ae,isBlob:Te,isFunction:fe,isStream:Ue,isURLSearchParams:Pe,isStandardBrowserEnv:Le,forEach:W,merge:z,extend:je,trim:Be,stripBOM:qe},x=b;function Y(r){return encodeURIComponent(r).replace(/%3A/gi,":").replace(/%24/g,"$").replace(/%2C/gi,",").replace(/%20/g,"+").replace(/%5B/gi,"[").replace(/%5D/gi,"]")}var le=function(e,t,s){if(!t)return e;var n;if(s)n=s(t);else if(x.isURLSearchParams(t))n=t.toString();else{var a=[];x.forEach(t,function(f,m){f===null||typeof f=="undefined"||(x.isArray(f)?m=m+"[]":f=[f],x.forEach(f,function(l){x.isDate(l)?l=l.toISOString():x.isObject(l)&&(l=JSON.stringify(l)),a.push(Y(m)+"="+Y(l))}))}),n=a.join("&")}if(n){var o=e.indexOf("#");o!==-1&&(e=e.slice(0,o)),e+=(e.indexOf("?")===-1?"?":"&")+n}return e},_e=b;function L(){this.handlers=[]}L.prototype.use=function(e,t,s){return this.handlers.push({fulfilled:e,rejected:t,synchronous:s?s.synchronous:!1,runWhen:s?s.runWhen:null}),this.handlers.length-1};L.prototype.eject=function(e){this.handlers[e]&&(this.handlers[e]=null)};L.prototype.forEach=function(e){_e.forEach(this.handlers,function(s){s!==null&&e(s)})};var De=L,ke=b,Ie=function(e,t){ke.forEach(e,function(n,a){a!==t&&a.toUpperCase()===t.toUpperCase()&&(e[t]=n,delete e[a])})},ce=function(e,t,s,n,a){return e.config=t,s&&(e.code=s),e.request=n,e.response=a,e.isAxiosError=!0,e.toJSON=function(){return{message:this.message,name:this.name,description:this.description,number:this.number,fileName:this.fileName,lineNumber:this.lineNumber,columnNumber:this.columnNumber,stack:this.stack,config:this.config,code:this.code,status:this.response&&this.response.status?this.response.status:null}},e},Fe=ce,de=function(e,t,s,n,a){var o=new Error(e);return Fe(o,t,s,n,a)},He=de,Me=function(e,t,s){var n=s.config.validateStatus;!s.status||!n||n(s.status)?e(s):t(He("Request failed with status code "+s.status,s.config,null,s.request,s))},T=b,Je=T.isStandardBrowserEnv()?function(){return{write:function(t,s,n,a,o,u){var f=[];f.push(t+"="+encodeURIComponent(s)),T.isNumber(n)&&f.push("expires="+new Date(n).toGMTString()),T.isString(a)&&f.push("path="+a),T.isString(o)&&f.push("domain="+o),u===!0&&f.push("secure"),document.cookie=f.join("; ")},read:function(t){var s=document.cookie.match(new RegExp("(^|;\\s*)("+t+")=([^;]*)"));return s?decodeURIComponent(s[3]):null},remove:function(t){this.write(t,"",Date.now()-864e5)}}}():function(){return{write:function(){},read:function(){return null},remove:function(){}}}(),ze=function(e){return/^([a-z][a-z\d\+\-\.]*:)?\/\//i.test(e)},Ke=function(e,t){return t?e.replace(/\/+$/,"")+"/"+t.replace(/^\/+/,""):e},Ve=ze,We=Ke,Xe=function(e,t){return e&&!Ve(t)?We(e,t):t},I=b,Ge=["age","authorization","content-length","content-type","etag","expires","from","host","if-modified-since","if-unmodified-since","last-modified","location","max-forwards","proxy-authorization","referer","retry-after","user-agent"],Ye=function(e){var t={},s,n,a;return e&&I.forEach(e.split(`
`),function(u){if(a=u.indexOf(":"),s=I.trim(u.substr(0,a)).toLowerCase(),n=I.trim(u.substr(a+1)),s){if(t[s]&&Ge.indexOf(s)>=0)return;s==="set-cookie"?t[s]=(t[s]?t[s]:[]).concat([n]):t[s]=t[s]?t[s]+", "+n:n}}),t},Q=b,Qe=Q.isStandardBrowserEnv()?function(){var e=/(msie|trident)/i.test(navigator.userAgent),t=document.createElement("a"),s;function n(a){var o=a;return e&&(t.setAttribute("href",o),o=t.href),t.setAttribute("href",o),{href:t.href,protocol:t.protocol?t.protocol.replace(/:$/,""):"",host:t.host,search:t.search?t.search.replace(/^\?/,""):"",hash:t.hash?t.hash.replace(/^#/,""):"",hostname:t.hostname,port:t.port,pathname:t.pathname.charAt(0)==="/"?t.pathname:"/"+t.pathname}}return s=n(window.location.href),function(o){var u=Q.isString(o)?n(o):o;return u.protocol===s.protocol&&u.host===s.host}}():function(){return function(){return!0}}();function X(r){this.message=r}X.prototype.toString=function(){return"Cancel"+(this.message?": "+this.message:"")};X.prototype.__CANCEL__=!0;var j=X,U=b,Ze=Me,er=Je,rr=le,tr=Xe,nr=Ye,sr=Qe,F=de,ar=_,ir=j,Z=function(e){return new Promise(function(s,n){var a=e.data,o=e.headers,u=e.responseType,f;function m(){e.cancelToken&&e.cancelToken.unsubscribe(f),e.signal&&e.signal.removeEventListener("abort",f)}U.isFormData(a)&&delete o["Content-Type"];var i=new XMLHttpRequest;if(e.auth){var l=e.auth.username||"",d=e.auth.password?unescape(encodeURIComponent(e.auth.password)):"";o.Authorization="Basic "+btoa(l+":"+d)}var E=tr(e.baseURL,e.url);i.open(e.method.toUpperCase(),rr(E,e.params,e.paramsSerializer),!0),i.timeout=e.timeout;function C(){if(!!i){var c="getAllResponseHeaders"in i?nr(i.getAllResponseHeaders()):null,O=!u||u==="text"||u==="json"?i.responseText:i.response,S={data:O,status:i.status,statusText:i.statusText,headers:c,config:e,request:i};Ze(function(k){s(k),m()},function(k){n(k),m()},S),i=null}}if("onloadend"in i?i.onloadend=C:i.onreadystatechange=function(){!i||i.readyState!==4||i.status===0&&!(i.responseURL&&i.responseURL.indexOf("file:")===0)||setTimeout(C)},i.onabort=function(){!i||(n(F("Request aborted",e,"ECONNABORTED",i)),i=null)},i.onerror=function(){n(F("Network Error",e,null,i)),i=null},i.ontimeout=function(){var O=e.timeout?"timeout of "+e.timeout+"ms exceeded":"timeout exceeded",S=e.transitional||ar.transitional;e.timeoutErrorMessage&&(O=e.timeoutErrorMessage),n(F(O,e,S.clarifyTimeoutError?"ETIMEDOUT":"ECONNABORTED",i)),i=null},U.isStandardBrowserEnv()){var R=(e.withCredentials||sr(E))&&e.xsrfCookieName?er.read(e.xsrfCookieName):void 0;R&&(o[e.xsrfHeaderName]=R)}"setRequestHeader"in i&&U.forEach(o,function(O,S){typeof a=="undefined"&&S.toLowerCase()==="content-type"?delete o[S]:i.setRequestHeader(S,O)}),U.isUndefined(e.withCredentials)||(i.withCredentials=!!e.withCredentials),u&&u!=="json"&&(i.responseType=e.responseType),typeof e.onDownloadProgress=="function"&&i.addEventListener("progress",e.onDownloadProgress),typeof e.onUploadProgress=="function"&&i.upload&&i.upload.addEventListener("progress",e.onUploadProgress),(e.cancelToken||e.signal)&&(f=function(c){!i||(n(!c||c&&c.type?new ir("canceled"):c),i.abort(),i=null)},e.cancelToken&&e.cancelToken.subscribe(f),e.signal&&(e.signal.aborted?f():e.signal.addEventListener("abort",f))),a||(a=null),i.send(a)})},p=b,ee=Ie,or=ce,ur={"Content-Type":"application/x-www-form-urlencoded"};function re(r,e){!p.isUndefined(r)&&p.isUndefined(r["Content-Type"])&&(r["Content-Type"]=e)}function fr(){var r;return(typeof XMLHttpRequest!="undefined"||typeof process!="undefined"&&Object.prototype.toString.call(process)==="[object process]")&&(r=Z),r}function lr(r,e,t){if(p.isString(r))try{return(e||JSON.parse)(r),p.trim(r)}catch(s){if(s.name!=="SyntaxError")throw s}return(t||JSON.stringify)(r)}var q={transitional:{silentJSONParsing:!0,forcedJSONParsing:!0,clarifyTimeoutError:!1},adapter:fr(),transformRequest:[function(e,t){return ee(t,"Accept"),ee(t,"Content-Type"),p.isFormData(e)||p.isArrayBuffer(e)||p.isBuffer(e)||p.isStream(e)||p.isFile(e)||p.isBlob(e)?e:p.isArrayBufferView(e)?e.buffer:p.isURLSearchParams(e)?(re(t,"application/x-www-form-urlencoded;charset=utf-8"),e.toString()):p.isObject(e)||t&&t["Content-Type"]==="application/json"?(re(t,"application/json"),lr(e)):e}],transformResponse:[function(e){var t=this.transitional||q.transitional,s=t&&t.silentJSONParsing,n=t&&t.forcedJSONParsing,a=!s&&this.responseType==="json";if(a||n&&p.isString(e)&&e.length)try{return JSON.parse(e)}catch(o){if(a)throw o.name==="SyntaxError"?or(o,this,"E_JSON_PARSE"):o}return e}],timeout:0,xsrfCookieName:"XSRF-TOKEN",xsrfHeaderName:"X-XSRF-TOKEN",maxContentLength:-1,maxBodyLength:-1,validateStatus:function(e){return e>=200&&e<300},headers:{common:{Accept:"application/json, text/plain, */*"}}};p.forEach(["delete","get","head"],function(e){q.headers[e]={}});p.forEach(["post","put","patch"],function(e){q.headers[e]=p.merge(ur)});var _=q,cr=b,dr=_,pr=function(e,t,s){var n=this||dr;return cr.forEach(s,function(o){e=o.call(n,e,t)}),e},pe=function(e){return!!(e&&e.__CANCEL__)},te=b,H=pr,hr=pe,mr=_,vr=j;function M(r){if(r.cancelToken&&r.cancelToken.throwIfRequested(),r.signal&&r.signal.aborted)throw new vr("canceled")}var yr=function(e){M(e),e.headers=e.headers||{},e.data=H.call(e,e.data,e.headers,e.transformRequest),e.headers=te.merge(e.headers.common||{},e.headers[e.method]||{},e.headers),te.forEach(["delete","get","head","post","put","patch","common"],function(n){delete e.headers[n]});var t=e.adapter||mr.adapter;return t(e).then(function(n){return M(e),n.data=H.call(e,n.data,n.headers,e.transformResponse),n},function(n){return hr(n)||(M(e),n&&n.response&&(n.response.data=H.call(e,n.response.data,n.response.headers,e.transformResponse))),Promise.reject(n)})},y=b,he=function(e,t){t=t||{};var s={};function n(i,l){return y.isPlainObject(i)&&y.isPlainObject(l)?y.merge(i,l):y.isPlainObject(l)?y.merge({},l):y.isArray(l)?l.slice():l}function a(i){if(y.isUndefined(t[i])){if(!y.isUndefined(e[i]))return n(void 0,e[i])}else return n(e[i],t[i])}function o(i){if(!y.isUndefined(t[i]))return n(void 0,t[i])}function u(i){if(y.isUndefined(t[i])){if(!y.isUndefined(e[i]))return n(void 0,e[i])}else return n(void 0,t[i])}function f(i){if(i in t)return n(e[i],t[i]);if(i in e)return n(void 0,e[i])}var m={url:o,method:o,data:o,baseURL:u,transformRequest:u,transformResponse:u,paramsSerializer:u,timeout:u,timeoutMessage:u,withCredentials:u,adapter:u,responseType:u,xsrfCookieName:u,xsrfHeaderName:u,onUploadProgress:u,onDownloadProgress:u,decompress:u,maxContentLength:u,maxBodyLength:u,transport:u,httpAgent:u,httpsAgent:u,cancelToken:u,socketPath:u,responseEncoding:u,validateStatus:f};return y.forEach(Object.keys(e).concat(Object.keys(t)),function(l){var d=m[l]||a,E=d(l);y.isUndefined(E)&&d!==f||(s[l]=E)}),s},me={version:"0.24.0"},br=me.version,G={};["object","boolean","number","function","string","symbol"].forEach(function(r,e){G[r]=function(s){return typeof s===r||"a"+(e<1?"n ":" ")+r}});var ne={};G.transitional=function(e,t,s){function n(a,o){return"[Axios v"+br+"] Transitional option '"+a+"'"+o+(s?". "+s:"")}return function(a,o,u){if(e===!1)throw new Error(n(o," has been removed"+(t?" in "+t:"")));return t&&!ne[o]&&(ne[o]=!0,console.warn(n(o," has been deprecated since v"+t+" and will be removed in the near future"))),e?e(a,o,u):!0}};function wr(r,e,t){if(typeof r!="object")throw new TypeError("options must be an object");for(var s=Object.keys(r),n=s.length;n-- >0;){var a=s[n],o=e[a];if(o){var u=r[a],f=u===void 0||o(u,a,r);if(f!==!0)throw new TypeError("option "+a+" must be "+f);continue}if(t!==!0)throw Error("Unknown option "+a)}}var Er={assertOptions:wr,validators:G},ve=b,Sr=le,se=De,ae=yr,D=he,ye=Er,N=ye.validators;function A(r){this.defaults=r,this.interceptors={request:new se,response:new se}}A.prototype.request=function(e){typeof e=="string"?(e=arguments[1]||{},e.url=arguments[0]):e=e||{},e=D(this.defaults,e),e.method?e.method=e.method.toLowerCase():this.defaults.method?e.method=this.defaults.method.toLowerCase():e.method="get";var t=e.transitional;t!==void 0&&ye.assertOptions(t,{silentJSONParsing:N.transitional(N.boolean),forcedJSONParsing:N.transitional(N.boolean),clarifyTimeoutError:N.transitional(N.boolean)},!1);var s=[],n=!0;this.interceptors.request.forEach(function(d){typeof d.runWhen=="function"&&d.runWhen(e)===!1||(n=n&&d.synchronous,s.unshift(d.fulfilled,d.rejected))});var a=[];this.interceptors.response.forEach(function(d){a.push(d.fulfilled,d.rejected)});var o;if(!n){var u=[ae,void 0];for(Array.prototype.unshift.apply(u,s),u=u.concat(a),o=Promise.resolve(e);u.length;)o=o.then(u.shift(),u.shift());return o}for(var f=e;s.length;){var m=s.shift(),i=s.shift();try{f=m(f)}catch(l){i(l);break}}try{o=ae(f)}catch(l){return Promise.reject(l)}for(;a.length;)o=o.then(a.shift(),a.shift());return o};A.prototype.getUri=function(e){return e=D(this.defaults,e),Sr(e.url,e.params,e.paramsSerializer).replace(/^\?/,"")};ve.forEach(["delete","get","head","options"],function(e){A.prototype[e]=function(t,s){return this.request(D(s||{},{method:e,url:t,data:(s||{}).data}))}});ve.forEach(["post","put","patch"],function(e){A.prototype[e]=function(t,s,n){return this.request(D(n||{},{method:e,url:t,data:s}))}});var gr=A,Cr=j;function $(r){if(typeof r!="function")throw new TypeError("executor must be a function.");var e;this.promise=new Promise(function(n){e=n});var t=this;this.promise.then(function(s){if(!!t._listeners){var n,a=t._listeners.length;for(n=0;n<a;n++)t._listeners[n](s);t._listeners=null}}),this.promise.then=function(s){var n,a=new Promise(function(o){t.subscribe(o),n=o}).then(s);return a.cancel=function(){t.unsubscribe(n)},a},r(function(n){t.reason||(t.reason=new Cr(n),e(t.reason))})}$.prototype.throwIfRequested=function(){if(this.reason)throw this.reason};$.prototype.subscribe=function(e){if(this.reason){e(this.reason);return}this._listeners?this._listeners.push(e):this._listeners=[e]};$.prototype.unsubscribe=function(e){if(!!this._listeners){var t=this._listeners.indexOf(e);t!==-1&&this._listeners.splice(t,1)}};$.source=function(){var e,t=new $(function(n){e=n});return{token:t,cancel:e}};var Rr=$,Or=function(e){return function(s){return e.apply(null,s)}},xr=function(e){return typeof e=="object"&&e.isAxiosError===!0},ie=b,Nr=oe,B=gr,$r=he,Ar=_;function be(r){var e=new B(r),t=Nr(B.prototype.request,e);return ie.extend(t,B.prototype,e),ie.extend(t,e),t.create=function(n){return be($r(r,n))},t}var w=be(Ar);w.Axios=B;w.Cancel=j;w.CancelToken=Rr;w.isCancel=pe;w.VERSION=me.version;w.all=function(e){return Promise.all(e)};w.spread=Or;w.isAxiosError=xr;K.exports=w;K.exports.default=w;var Tr=K.exports;const h=Tr.create({baseURL:"",timeout:60*1e3});h.interceptors.request.use(r=>(r.headers.token=sessionStorage.token,r.method=="post"&&(r.headers["Content-Type"]="application/json"),r),r=>(console.log(r),Promise.reject(r)));h.interceptors.response.use(r=>r.data,r=>(console.log("err"+r),r.response.status==401&&(sessionStorage.clear(),globalThis.$router.push("/login")),Ee.exports.Message({message:r.message,type:"error",duration:5*1e3}),Promise.reject(r)));const v="/api";function Pr(r,e){return h({url:v+"/login?userName="+r+"&password="+e,method:"post",params:{}})}function Br(r,e){let t=v+"/editConfig?deviceId="+e;return h.post(t,r)}function Lr(){return h({url:v+"/deviceGroups",method:"get",params:{}})}function jr(r,e,t,s,n,a,o,u,f,m,i,l,d,E,C){let R="";n=n.replace(/\+/g,"%2B"),a!=null&&a!=""&&(R+="&startTime="+a/1e3),o!=null&&o!=""&&(R+="&endTime="+o/1e3);let c="";return u!=null&&u!=""&&(c+="&minDepth="+u),i!=null&&i!=""&&(c+="&maxDepth="+i),l!=null&&l!=""&&(c+="&minAvgAsh="+l),d!=null&&d!=""&&(c+="&maxAvgAsh="+d),f!=null&&f!=""&&(c+="&minAsh="+f),E!=null&&E!=""&&(c+="&maxAsh="+E),m!=null&&m!=""&&(c+="&minPileTime="+m),C!=null&&C!=""&&(c+="&maxPileTime="+C),h({url:v+"/historys?deviceType="+r+"&page="+e+"&deviceKey="+t+"&size="+s+"&pileDescribe="+n+R+c,method:"get",params:{}})}function qr(r,e,t,s,n){let a="";return e!=null&&e!=""&&(a+="&deviceKey="+e),t!=null&&t!=""&&(a+="&beginDate="+t),s!=null&&s!=""&&(a+="&endDate="+s),n!=null&&n!=""&&(a+="&deviceType="+n),h({url:v+"/analysis?limit="+r+a,method:"get",params:{}})}function _r(r,e,t,s){return t===""?t=0:t=t/1e3,s===""?s=9999999999:s=s/1e3,h({url:v+"/historyAnalysis?deviceKey="+r+"&deviceType="+e+"&beginDate="+t+"&endDate="+s,methods:"get",params:{}})}function Dr(r,e,t,s,n){return n=n.replace(/\+/g,"%2B"),h({url:v+"/historyWarns?deviceType="+r+"&page="+e+"&deviceKey="+t+"&size="+s+"&pileDescribe="+n,method:"get",params:{}})}function kr(r,e,t){return h({url:v+"/devices?size="+r+"&current="+e+"&deviceType="+t,method:"get",params:{}})}function Ir(r,e){return h({url:v+"/lastData?deviceKey="+r+"&pileId="+e,method:"get",params:{}})}function Fr(r,e,t){return h({url:v+"/datas?deviceKey="+r+"&pileId="+e+"&fields="+t,method:"get",params:{}})}function Hr(r){return h({url:v+"/pileIds?deviceKey="+r,method:"get",params:{}})}function Mr(){return h({url:v+"/groupSum",method:"get",params:{}})}function Jr(){return h({url:v+"/newest",method:"get",params:{}})}function zr(r,e,t){return h({url:v+"/historyBacks?page="+r+"&size="+e+"&deviceKey="+t,method:"get",params:{}})}export{qr as a,Ir as b,zr as c,Fr as d,Br as e,Lr as f,jr as g,Mr as h,Dr as i,_r as j,Pr as l,Jr as n,Hr as p,kr as q};
