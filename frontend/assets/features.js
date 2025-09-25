import {ref} from "vue";
import $ from "jquery";
export const isPopupVisible = ref(false);
export const handlePopup = (p) => {
    isPopupVisible.value = p;
}
export function setupMenuToggle() {
    $(document).ready(() => {
        $("nav").hide();
        $("#menubar button").click(function () {
            $("nav").slideToggle(700);
        });
    });
}
export function customPopup(title, content, isCritical) {
    let popup = document.createElement("div");
    popup.style.cssText = `position: fixed; top: 0; left: 0;
    width: 100%; height: 100%; background-color: rgba(0, 0, 0, 0.5);
    display: flex; justify-content: center; align-items: center; z-index: 2`;

    let popupContent = document.createElement("div");
    popupContent.style.cssText = `background-color: ${isCritical ? "#f93838" : "white"};
    color: ${isCritical ? "white" : "black"}; padding: 0.8em; border-radius: 10px; text-align: center;
    position: relative; width: 400px`;

    let closeBtn = document.createElement("span");
    closeBtn.innerHTML = "&times;";
    closeBtn.style.cssText = 'font-size: 1.5em; float: right; cursor: pointer; margin: -8px 0px 5px 5px';
    closeBtn.onclick = () => { document.body.removeChild(popup)};

    let headLine = document.createElement("h3");
    let message = document.createElement("p");
    headLine.textContent = title;
    message.textContent = content;

    popupContent.appendChild(closeBtn);
    popupContent.appendChild(headLine);
    popupContent.appendChild(message);
    popup.appendChild(popupContent);
    document.body.appendChild(popup);
}