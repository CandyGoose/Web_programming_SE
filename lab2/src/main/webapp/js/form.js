document.querySelectorAll("form").forEach((form) =>
    form.querySelectorAll("button.value-button, input[type=button].value-button")
        .forEach(button => button.addEventListener("click", () => {
            let value = button.value
            form.querySelectorAll(`.value-button__input[name=${button.attributes.getNamedItem("name").value}]`)
                .forEach(input => {
                    input.value = value
                    input.dispatchEvent(new Event("change"))
                })
        })))


document.querySelectorAll(".radio-group")
    .forEach((group) => {
            let radios = group.querySelectorAll("input.radio-group__button, input[type=radio].radio-group__button");
        radios.forEach((radio) => {
                radio.addEventListener("click", () => {
                    radios.forEach((e) => e.classList.remove("active"));
                    radio.classList.add("active");
                })
            })
        }
    )

document.querySelectorAll("form").forEach((form) =>
    form.querySelectorAll("input.value-button, input[type=radio].value-button")
        .forEach(radio => radio.addEventListener("click", () => {
            let value = radio.value
            form.querySelectorAll(`.value-button__input[name=${radio.attributes.getNamedItem("name").value}]`)
                .forEach(input => {
                    input.value = value
                    input.dispatchEvent(new Event("change"))
                })
        })))


