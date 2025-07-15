let todasFaturas = []; // Array global para armazenar as faturas do cliente
let clienteId = null; // ID do cliente tirado da URL

document.addEventListener("DOMContentLoaded", () => {
  const urlParams = new URLSearchParams(window.location.search);
  clienteId = urlParams.get("clienteId");

  if (!clienteId) {
    alert("ID do cliente não encontrado na URL.");
    return;
  }

  // Requisição ds faturas do cliente pelo ID
  fetch(`/faturas/cliente/${clienteId}`)
    .then(res => res.json())
    .then(faturas => {
      todasFaturas = faturas;
      renderizarFaturas(todasFaturas);
    })
    .catch(err => {
      console.error("Erro ao buscar faturas:", err);
      alert("Erro ao carregar faturas do cliente.");
    });

  // Envio do formulário de nova fatura
  const form = document.getElementById("nova-fatura-form");
  if (form) {
    form.addEventListener("submit", function (e) {
      e.preventDefault();

      // Cria o objeto da nova fatura com os valores do formulário
      const novaFatura = {
        valor: document.getElementById("valor").value,
        dataVencimento: document.getElementById("data-vencimento").value,
        clienteId: parseInt(clienteId),
        status: document.getElementById("status-fatura").value
      };

      // Envia a fatura para o backend
      fetch("/faturas", {
        method: "POST",
        headers: {
          "Content-Type": "application/json"
        },
        body: JSON.stringify(novaFatura)
      })
        .then(res => {
          if (res.ok) {
            alert("Fatura adicionada!");
            document.getElementById("nova-fatura-form").reset();
            location.reload();
          } else {
            throw new Error("Erro ao adicionar fatura.");
          }
        })
        .catch(err => {
          console.error(err);
          alert("Erro ao salvar fatura.");
        });
    });
  }

  // Botões
  document.getElementById("btn-voltar").addEventListener("click", voltarParaClientes);
  document.getElementById("btn-atrasadas").addEventListener("click", mostrarAtrasadas);
  document.getElementById("btn-nova-fatura").addEventListener("click", () => mostrarFormularioNovaFatura());

});

//Funções auxiliares e de requisição http via fetch para registrar pagamento

function renderizarFaturas(faturas) {
  const container = document.getElementById("faturas-container");
  container.innerHTML = "";

  if (faturas.length === 0) {
    container.innerHTML = "<p>Nenhuma fatura encontrada.</p>";
    return;
  }

  faturas.forEach(fatura => {
    const div = document.createElement("div");
    div.classList.add("fatura");

    div.innerHTML = `
      <p><strong>Valor:</strong> R$ ${fatura.valor.toFixed(2)}</p>
      <p><strong>Data de Vencimento:</strong> ${formatarData(fatura.dataVencimento)}</p>
      <p><strong>Status:</strong> ${
        fatura.status === "P" ? "Paga" :
        fatura.status === "A" ? "Atrasada" :
        fatura.status === "B" ? "Aberta" :
        fatura.status
      }</p>
      <p><strong>Data de Pagamento:</strong> ${fatura.dataPagamento ? formatarData(fatura.dataPagamento) : "-"}</p>
      ${fatura.status !== "P"
        ? `<button onclick="registrarPagamento(${fatura.id})">Registrar Pagamento</button>`
        : ""
      }
    `;
    container.appendChild(div);
  });
}

function registrarPagamento(faturaId) {
  fetch(`/faturas/${faturaId}/pagamento`, {
    method: "PUT"
  })
    .then(response => {
      if (response.ok) {
        alert("Pagamento registrado com sucesso!");
        location.reload();
      } else {
        alert("Erro ao registrar pagamento.");
      }
    })
    .catch(err => {
      console.error("Erro no pagamento:", err);
      alert("Erro de comunicação com o servidor.");
    });
}

function cancelarFatura() {
  document.getElementById("nova-fatura-form").reset();
  document.getElementById("form-fatura").style.display = "none";
}

function formatarData(isoDate) {
  const data = new Date(isoDate);
  return data.toLocaleDateString("pt-BR", {
    day: "2-digit",
    month: "2-digit",
    year: "2-digit"
  });
}

function voltarParaClientes() {
  window.location.href = "/html/index.html";
}

function mostrarAtrasadas() {
  const atrasadas = todasFaturas.filter(f => f.status === "A");
  renderizarFaturas(atrasadas);
}

function mostrarFormularioNovaFatura() {
  document.getElementById("form-fatura").style.display = "block";
}


