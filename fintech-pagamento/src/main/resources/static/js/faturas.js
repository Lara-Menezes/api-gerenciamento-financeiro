document.addEventListener("DOMContentLoaded", () => {
  const urlParams = new URLSearchParams(window.location.search);
  const clienteId = urlParams.get("clienteId");

  if (!clienteId) {
    alert("ID do cliente não encontrado na URL.");
    return;
  }

  fetch(`/clientes/${clienteId}`)
    .then(res => res.json())
    .then(cliente => {
      const faturas = cliente.faturas;
      const container = document.getElementById("faturas-container");
      container.innerHTML = ""; // limpa antes

      faturas.forEach(fatura => {
        const div = document.createElement("div");
        div.classList.add("fatura");

        div.innerHTML = `
          <p><strong>Valor:</strong> R$ ${fatura.valor.toFixed(2)}</p>
          <p><strong>Data de Vencimento:</strong> ${fatura.dataVencimento}</p>
          <p><strong>Status:</strong> ${
            fatura.status === "P" ? "Paga" :
            fatura.status === "A" ? "Atrasada" :
            fatura.status === "B" ? "Aberta" :
            fatura.status
          }</p>
          <p><strong>Data de Pagamento:</strong> ${fatura.dataPagamento || "-"}</p>
          ${
            fatura.status !== "P"
              ? `<button onclick="registrarPagamento(${fatura.id})">Pagar</button>`
              : ""
          }
        `;

        container.appendChild(div);
      });
    })
    .catch(err => {
      console.error("Erro ao buscar faturas:", err);
      alert("Erro ao carregar faturas do cliente.");
    });
});

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

