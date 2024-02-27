const storedData = localStorage.getItem("data");
        const data = JSON.parse(storedData);

        function display(data) {
            let container = document.querySelector('.container');

            let i = 1;
            data.forEach(question => {
                let div = document.createElement("div");
                div.className = "question-container"
                let questionElement = document.createElement("h2");
                questionElement.innerText = `${i}. ${question.title}`;
                i++;
                div.appendChild(questionElement);



                for (let i = 1; i < 5; i++) {
                    let option = document.createElement('input');
                    option.type = 'radio';
                    option.name = `option${question.id}`;
                    option.value = `option${i}`;
                    let label = document.createElement('label');
                    label.setAttribute('for', `option${i}`);
                    label.innerText = question[`option${i}`];
                    let optionDiv = document.createElement("div");
                    optionDiv.className = "option";
                    optionDiv.append(option);
                    optionDiv.append(label);
                    div.appendChild(optionDiv);
                }

                document.querySelector(".main").append(div);
            });
        }

        display(data);

        let options = document.querySelectorAll(".option");
        options.forEach(option => {
            let innerInput = option.querySelector('input[type="radio"]');
            option.addEventListener('click', (event) => {
                innerInput.checked = true;

                // Reset styles for options within the same question container
                let questionContainer = option.closest('.question-container');
                let optionsInContainer = questionContainer.querySelectorAll('.option');
                optionsInContainer.forEach(opt => {
                    opt.style.backgroundColor = "white";
                    opt.style.color = "black";
                });

                // Set styles for the clicked option
                option.style.backgroundColor = "#ad9cf7";
                option.style.color = "white";
            });
        });





        document.querySelector(".nextBtn").addEventListener("click", () => {
            let selectedAnswers = [];
            data.forEach(element => {
                let optionSelected = document.querySelector(`input[name="option${element.id}"]:checked`)
                if (optionSelected !== null) {
                    let answer;
                    if (optionSelected.value === "option1") {
                        answer = "A";
                    }
                    else if (optionSelected.value === "option2") {
                        answer = "B";
                    }
                    else if (optionSelected.value === "option3") {
                        answer = "C";
                    }
                    else {
                        answer = "D";
                    }
                    selectedAnswers.push({
                        "questionId": element.id,
                        "answer": answer
                    })
                }
            })
            sendDataToBackend(selectedAnswers);
        });
        async function sendDataToBackend(answers) {
            try {
                let response = await fetch('http://localhost:8080/answers', {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json',
                    },
                    body: JSON.stringify({ answers: answers }),
                });
                const data = await response.json();
                localStorage.setItem("quizResult", data);
                window.location.href = "score.html";

            }
            catch (error) {
                console.log(error);
            }
        }