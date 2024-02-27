async function selectCategories() {
    let checkedBoxes = document.querySelectorAll('input[type="checkbox"]:checked');
    if (checkedBoxes.length < 2) {
        alert("Select atleast two categories");
    }
    else {
        let selectedCategories = Array.from(checkedBoxes).map(checkBox => (checkBox.value));
        await sendToBackend(selectedCategories);
    }
}
async function sendToBackend(selectedCategories) {
    try {
        const response = await fetch('http://localhost:8080/categories', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({ categories: selectedCategories }),
        });
        const data = await response.json();
        console.log(data);
        localStorage.setItem("data", JSON.stringify(data));
        window.location.href = "main.html";
    } catch (error) {
        console.error('There was a problem with the fetch operation:', error);
    }
}


const checkboxes = document.querySelectorAll('input[type="checkbox"]');
checkboxes.forEach(function (checkbox) {
    checkbox.addEventListener('change', function () {
        const label = this.nextElementSibling;
        const div = this.closest('div');
        if (this.checked) {
            label.style.color = 'blue';
            div.style.border = '2px solid blue';// Change this to the desired color
        } else {
            label.style.color = '';
            div.style.border = '' // Revert to default color
        }
    });
});
