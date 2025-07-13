document.addEventListener("DOMContentLoaded", function () {
  fetch("/clientes")
    .then(response => {
      if (!response.ok) {
        throw new Error("Erro ao carregar clientes.");
      }
      return response.json();
    })
    .then(clientes => {
      const tbody = document.querySelector("#tabela-clientes tbody");
      tbody.innerHTML = ""; // limpa a tabela antes de inserir

      clientes.forEach(cliente => {
        const tr = document.createElement("tr");
        tr.innerHTML = `
          <td>${cliente.nome}</td>
          <td>${cliente.cpf}</td>
          <td>${calcularIdade(cliente.dataNascimento)}</td>
          <td>${cliente.statusBloqueio === "B" ? "Bloqueado" : "Ativo"}</td>
          <td>R$ ${cliente.limiteCredito.toFixed(2)}</td>
          <td>
            <button onclick="verFaturas(${cliente.id})">Ver Faturas</button>
          </td>
        `;
        tbody.appendChild(tr);
      });
    })
    .catch(error => {
      console.error("Erro ao buscar clientes:", error);
      alert("Erro ao carregar a lista de clientes.");
    });
});

function calcularIdade(dataNascimento) {
  const nascimento = new Date(dataNascimento);
  const hoje = new Date();
  let idade = hoje.getFullYear() - nascimento.getFullYear();
  const m = hoje.getMonth() - nascimento.getMonth();
  if (m < 0 || (m === 0 && hoje.getDate() < nascimento.getDate())) {
    idade--;
  }
  return idade;
}

function verFaturas(clienteId) {
  window.location.href = `/html/faturas.html?clienteId=${clienteId}`;
}


