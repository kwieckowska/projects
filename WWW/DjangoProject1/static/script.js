document.addEventListener('DOMContentLoaded', function() {

    // usuwanie przepisu
    const deleteLinks = document.querySelectorAll('.delete-confirm');
    deleteLinks.forEach(link => {
        link.addEventListener('click', function(event) {
            if (!confirm('Czy na pewno chcesz usunąć ten przepis?')) {
                event.preventDefault();
            }
        });
    });

    // walidacja 'na zywo'
    const recipeForm = document.getElementById('recipe-form');

    if (recipeForm) {
        const fields = {
            title: document.querySelector('input[name="title"]'),
            time: document.querySelector('input[name="preparation_time"]'),
            ingredients: document.querySelector('textarea[name="ingredients"]'),
            description: document.querySelector('textarea[name="description"]')
        };  // taka forma umozliwia pozniej petle foreach, nie trzeba pisac dla kazdej zmiennej walidacji od nowa

        const validators = {
            title: (val) => {
                if (!val || val.trim().length === 0) return 'Podaj tytuł przepisu.';
                if (val.trim().length < 3) return 'Tytuł jest za krótki (min. 3 znaki).';
                return null;
            },
            time: (val) => {
                if (!val || val <= 0) return 'Czas musi być większy od 0.';
                return null;
            },
            ingredients: (val) => {
                if (!val || val.trim().length < 10) return 'Wymień składniki (min. 10 znaków).';
                return null;
            },
            description: (val) => {
                if (!val || val.trim().length < 10) return 'Opisz przygotowanie (min. 10 znaków).';
                return null;
            }
        };

        const handleValidation = (fieldInput, validatorFn) => {
            if (!fieldInput) return true;

            const errorMessage = validatorFn(fieldInput.value);
            const parent = fieldInput.parentNode;
            let errorDiv = parent.querySelector('.form-error-message');

            if (errorMessage) {
                //czerwona ramka
                fieldInput.classList.add('input-error');

                if (!errorDiv) {
                    errorDiv = document.createElement('div');
                    errorDiv.classList.add('form-error-message');
                    parent.insertBefore(errorDiv, fieldInput.nextSibling);
                }
                errorDiv.innerText = errorMessage;
                return false;
            } else {
                // usuwamy znaki bledow
                fieldInput.classList.remove('input-error');
                if (errorDiv) {
                    errorDiv.remove();
                }
                return true;
            }
        };

        Object.keys(fields).forEach(key => {
            const field = fields[key];
            if (field) {
                field.addEventListener('blur', () => handleValidation(field, validators[key]));
                field.addEventListener('input', () => handleValidation(field, validators[key]));
            }
        });

        recipeForm.addEventListener('submit', function(event) {
            let isFormValid = true;
            Object.keys(fields).forEach(key => {
                if (!handleValidation(fields[key], validators[key])) {
                    isFormValid = false;
                }
            });
            if (!isFormValid) {
                event.preventDefault();
            }
        });
    }

    // podglad zdjecia przy jego dodawaniu do przepisu --- Użycie JavaScriptu (wykraczające poza walidację formularzy)
    const imageInput = document.querySelector('input[type="file"]');
    if (imageInput) {
        imageInput.addEventListener('change', function(e) {
            const file = e.target.files[0];
            if (file) {
                let preview = document.getElementById('js-img-preview');
                if (!preview) {
                    preview = document.createElement('img');
                    preview.id = 'js-img-preview';
                    preview.classList.add('image-preview-box');
                    imageInput.parentNode.appendChild(preview);
                }
                preview.src = URL.createObjectURL(file);
            }
        });
    }
});