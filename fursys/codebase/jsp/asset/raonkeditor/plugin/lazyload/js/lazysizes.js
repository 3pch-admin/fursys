(function(p,z){var t=z(p,p.document,Date);p.lazySizes=t;"object"==typeof module&&module.exports&&(module.exports=t)})("undefined"!=typeof window?window:{},function(p,z,t){var A,b;(function(){var c,a={lazyClass:"dext_lazyload",loadedClass:"dext_lazyloaded",loadingClass:"dext_lazyloading",preloadClass:"dext_lazypreload",errorClass:"dext_lazyerror",autosizesClass:"dext_lazyautosizes",fastLoadedClass:"dext_ls-is-cached",iframeLoadMode:0,srcAttr:"dext-src",srcsetAttr:"dext-srcset",sizesAttr:"dext-sizes",
minSize:40,customMedia:{},init:!1,expFactor:1.5,hFac:.8,loadMode:2,loadHidden:!0,ricTimeout:0,throttleDelay:125};b=p.lazySizesConfig||p.lazysizesConfig||{};for(c in a)c in b||(b[c]=a[c])})();if(!z||!z.getElementsByClassName)return{init:function(){},cfg:b,noSupport:!0};var h=p,n=h.document,B=n.documentElement,U=h.HTMLPictureElement,q=h.addEventListener.bind(h),m=h.setTimeout,aa=h.requestAnimationFrame||m,I=h.requestIdleCallback,ba=/^picture$/i,V=["load","error","lazyincluded","_lazyloaded"],y={},ha=
Array.prototype.forEach,J=function(c,a){y[a]||(y[a]=new RegExp("(\\s|^)"+a+"(\\s|$)"));return y[a].test(c.getAttribute("class")||"")&&y[a]},K=function(c,a){J(c,a)||c.setAttribute("class",(c.getAttribute("class")||"").trim()+" "+a)},W=function(c,a){var b;(b=J(c,a))&&c.setAttribute("class",(c.getAttribute("class")||"").replace(b," "))},X=function(c,a,b){var e=b?"addEventListener":"removeEventListener";b&&X(c,a);V.forEach(function(b){c[e](b,a)})},L=function(b,a,d,e,g){var k=n.createEvent("Event");d||
(d={});d.instance=A;k.initEvent(a,!e,!g);k.detail=d;b.dispatchEvent(k);return k},Y=function(c,a){var d;!U&&(d=h.picturefill||b.pf)?(a&&a.src&&!c.getAttribute("srcset")&&c.setAttribute("srcset",a.src),d({reevaluate:!0,elements:[c]})):a&&a.src&&(c.src=a.src)},M=function(c,a,d){for(d=d||c.offsetWidth;d<b.minSize&&a&&!c._lazysizesWidth;)d=a.offsetWidth,a=a.parentNode;return d},N=function(){var b,a,d=[],e=[],g=d,k=function(){var k=g;g=d.length?e:d;b=!0;for(a=!1;k.length;)k.shift()();b=!1},u=function(d,
e){b&&!e?d.apply(this,arguments):(g.push(d),a||(a=!0,(n.hidden?m:aa)(k)))};u._lsFlush=k;return u}(),R=function(b,a){return a?function(){N(b)}:function(){var a=this,e=arguments;N(function(){b.apply(a,e)})}},ia=function(c){var a,d=0,e=b.throttleDelay,g=b.ricTimeout,k=function(){a=!1;d=t.now();c()},u=I&&49<g?function(){I(k,{timeout:g});g!==b.ricTimeout&&(g=b.ricTimeout)}:R(function(){m(k)},!0);return function(b){var c;if(b=!0===b)g=33;a||(a=!0,c=e-(t.now()-d),0>c&&(c=0),b||9>c?u():m(u,c))}},ca=function(b){var a,
d,e=function(){a=null;b()},g=function(){var b=t.now()-d;99>b?m(g,99-b):(I||e)(e)};return function(){d=t.now();a||(a=m(g,99))}},Q=function(){var c,a,d,e,g,k,u,E,F,G,p,z,y=/^img$/i,w=/^iframe$/i,I="onscroll"in h&&!/(gle|ing)bot/.test(navigator.userAgent),S=0,x=0,H=-1,M=function(b){x--;if(!b||0>x||!b.target)x=0},Q=function(b){null==z&&(z="hidden"==(getComputedStyle(n.body,null)||{}).visibility);return z||!("hidden"==(getComputedStyle(b.parentNode,null)||{}).visibility&&"hidden"==(getComputedStyle(b,
null)||{}).visibility)},da=function(){var O,r,f,d,g,h,l,m,t,q,y,v=A.elements;if((e=b.loadMode)&&8>x&&(O=v.length)){r=0;for(H++;r<O;r++)if(v[r]&&!v[r]._lazyRace)if(!I||A.prematureUnveil&&A.prematureUnveil(v[r]))P(v[r]);else{(m=v[r].getAttribute("data-expand"))&&(h=1*m)||(h=S);q||(q=!b.expand||1>b.expand?500<B.clientHeight&&500<B.clientWidth?500:370:b.expand,A._defEx=q,f=q*b.expFactor,y=b.hFac,z=null,S<f&&1>x&&2<H&&2<e&&!n.hidden?(S=f,H=0):S=1<e&&1<H&&6>x?q:0);t!==h&&(k=innerWidth+h*y,u=innerHeight+
h,l=-1*h,t=h);f=v[r].getBoundingClientRect();if((f=(p=f.bottom)>=l&&(E=f.top)<=u&&(G=f.right)>=l*y&&(F=f.left)<=k&&(p||G||F||E)&&(b.loadHidden||Q(v[r])))&&!(f=a&&3>x&&!m&&(3>e||4>H))){var C=v[r];f=h;var w=void 0,D=C,C=Q(C);E-=f;p+=f;F-=f;for(G+=f;C&&(D=D.offsetParent)&&D!=n.body&&D!=B;)(C=0<((getComputedStyle(D,null)||{}).opacity||1))&&"visible"!=(getComputedStyle(D,null)||{}).overflow&&(w=D.getBoundingClientRect(),C=G>w.left&&F<w.right&&p>w.top-1&&E<w.bottom+1);f=C}if(f){if(P(v[r]),g=!0,9<x)break}else!g&&
a&&!d&&4>x&&4>H&&2<e&&(c[0]||b.preloadAfterLoad)&&(c[0]||!m&&(p||G||F||E||"auto"!=v[r].getAttribute(b.sizesAttr)))&&(d=c[0]||v[r])}d&&!g&&P(d)}},l=ia(da),fa=function(O){var a=O.target;a._lazyCache?delete a._lazyCache:(M(O),K(a,b.loadedClass),W(a,b.loadingClass),X(a,ea),L(a,"lazyloaded"))},U=R(fa),ea=function(b){U({target:b.target})},V=function(a,c){var f=a.getAttribute("data-load-mode")||b.iframeLoadMode;0==f?a.contentWindow.location.replace(c):1==f&&(a.src=c)},ja=function(a){var c,f=a.getAttribute(b.srcsetAttr);
(c=b.customMedia[a.getAttribute("data-media")||a.getAttribute("media")])&&a.setAttribute("media",c);f&&a.setAttribute("srcset",f)},ka=R(function(a,c,f,g,k){var h,e,u,l;(u=L(a,"lazybeforeunveil",c)).defaultPrevented||(g&&(f?K(a,b.autosizesClass):a.setAttribute("sizes",g)),g=a.getAttribute(b.srcsetAttr),f=a.getAttribute(b.srcAttr),k&&(e=(h=a.parentNode)&&ba.test(h.nodeName||"")),l=c.firesLoad||"src"in a&&(g||f||e),u={target:a},K(a,b.loadingClass),l&&(clearTimeout(d),d=m(M,2500),X(a,ea,!0)),e&&ha.call(h.getElementsByTagName("source"),
ja),g?a.setAttribute("srcset",g):f&&!e&&(w.test(a.nodeName)?V(a,f):a.src=f),k&&(g||e)&&Y(a,{src:f}));a._lazyRace&&delete a._lazyRace;W(a,b.lazyClass);N(function(){var c=a.complete&&1<a.naturalWidth;if(!l||c)c&&K(a,b.fastLoadedClass),fa(u),a._lazyCache=!0,m(function(){"_lazyCache"in a&&delete a._lazyCache},9);"lazy"==a.loading&&x--},!0)}),P=function(c){if(!c._lazyRace){var g,f=y.test(c.nodeName),d=f&&(c.getAttribute(b.sizesAttr)||c.getAttribute("sizes")),k="auto"==d;if(!k&&a||!f||!c.getAttribute("src")&&
!c.srcset||c.complete||J(c,b.errorClass)||!J(c,b.lazyClass))g=L(c,"lazyunveilread").detail,k&&Z.updateElem(c,!0,c.offsetWidth),c._lazyRace=!0,x++,ka(c,g,k,d,f)}},la=ca(function(){b.loadMode=3;l()}),ga=function(){3==b.loadMode&&(b.loadMode=2);la()},T=function(){a||(999>t.now()-g?m(T,999):(a=!0,b.loadMode=3,l(),q("scroll",ga,!0)))};return{_:function(){g=t.now();A.elements=n.getElementsByClassName(b.lazyClass);c=n.getElementsByClassName(b.lazyClass+" "+b.preloadClass);q("scroll",l,!0);q("resize",l,!0);
q("pageshow",function(a){if(a.persisted){var c=n.querySelectorAll("."+b.loadingClass);c.length&&c.forEach&&aa(function(){c.forEach(function(a){a.complete&&P(a)})})}});h.MutationObserver?(new MutationObserver(l)).observe(B,{childList:!0,subtree:!0,attributes:!0}):(B.addEventListener("DOMNodeInserted",l,!0),B.addEventListener("DOMAttrModified",l,!0),setInterval(l,999));q("hashchange",l,!0);"focus mouseover click load transitionend animationend".split(" ").forEach(function(a){n.addEventListener(a,l,
!0)});/d$|^c/.test(n.readyState)?T():(q("load",T),n.addEventListener("DOMContentLoaded",l),m(T,2E4));A.elements.length?(da(),N._lsFlush()):l()},checkElems:l,unveil:P,_aLSL:ga}}(),Z=function(){var c,a=R(function(a,b,c,d){var e,h;a._lazysizesWidth=d;d+="px";a.setAttribute("sizes",d);if(ba.test(b.nodeName||""))for(b=b.getElementsByTagName("source"),e=0,h=b.length;e<h;e++)b[e].setAttribute("sizes",d);c.detail.dataAttr||Y(a,c.detail)}),d=function(b,c,d){var e=b.parentNode;e&&(d=M(b,e,d),c=L(b,"lazybeforesizes",
{width:d,dataAttr:!!c}),c.defaultPrevented||(d=c.detail.width)&&d!==b._lazysizesWidth&&a(b,e,c,d))},e=ca(function(){var a,b=c.length;if(b)for(a=0;a<b;a++)d(c[a])});return{_:function(){c=n.getElementsByClassName(b.autosizesClass);q("resize",e)},checkElems:e,updateElem:d}}(),w=function(b){if(!w.i&&n.getElementsByClassName||1==b)w.i=!0,Z._(),Q._()};m(function(){b.init&&w()});return A={cfg:b,autoSizer:Z,loader:Q,init:w,uP:Y,aC:K,rC:W,hC:J,fire:L,gW:M,rAF:N,setWindow:function(b){h=b;n=h.document;B=n.documentElement;
q=h.addEventListener.bind(h);m=h.setTimeout}}});
