import {ref} from "vue";
export const isPopupVisible = ref(false);
export const popup = ref({
    title: "",
    content: "",
    isCritical: false,
    icon: "info",
});

export const handlePopup = (p) => {
    isPopupVisible.value = p;
}

export function customPopup(title, content, isCritical = false, icon) {
    const validIcons = ["info", "warning", "error"];
    const selectedIcon = validIcons.includes(icon)
        ? icon
        : isCritical ? "error" : "info";

    popup.value = {
        title: String(title),
        content: String(content),
        isCritical,
        icon: selectedIcon,
    };
    isPopupVisible.value = true;
}