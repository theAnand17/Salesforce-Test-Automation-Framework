let map = new Map();
function xpathSingleEval(expression, contextNode) {
    return document.evaluate(expression, contextNode,
        null, XPathResult.FIRST_ORDERED_NODE_TYPE, null).singleNodeValue;
}

let rootNode = xpathSingleEval("//div[contains(@class,'slds-modal__container')]", document);
let footerList = document.evaluate(".//footer[contains(@class,'slds-scope footer')]//button", rootNode, null, XPathResult.ORDERED_NODE_ITERATOR_TYPE, null);

let footerListNode = footerList.iterateNext();
while(footerListNode)
{
   if(footerListNode)
      {
        map.set(footerListNode.textContent,footerListNode);
      }
  footerListNode = footerList.iterateNext();
}

console.log(map);
return Object.fromEntries(map);


