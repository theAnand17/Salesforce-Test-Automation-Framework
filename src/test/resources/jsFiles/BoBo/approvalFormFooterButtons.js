let map = new Map();
function xpathSingleEval(expression, contextNode) {
    return document.evaluate(expression, contextNode,
        null, XPathResult.FIRST_ORDERED_NODE_TYPE, null).singleNodeValue;
}

let rootNode = xpathSingleEval("//div[contains(@class,'modal-container slds-modal__container')]", document);
let listItem = document.evaluate(".//div[contains(@class,'slds-modal__footer')]//div//button", rootNode, null, XPathResult.ORDERED_NODE_ITERATOR_TYPE, null);
let listNode = listItem.iterateNext();
while (listNode) {
    if(listNode) {
        let button      = xpathSingleEval(".//span", listNode);
        if(button){
            map.set(button.textContent, button);
           }
       }
    listNode = listItem.iterateNext();
}

console.log(map);

return Object.fromEntries(map);