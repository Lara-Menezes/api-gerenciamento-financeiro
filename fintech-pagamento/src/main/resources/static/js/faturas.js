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
      const tbody = document.getElementById("faturas-body");
      tbody.innerHTML = "";

      faturas.forEach(fatura => {
        const tr = document.createElement("tr");

        tr.innerHTML = `
          <td>R$ ${fatura.valor.toFixed(2)}</td>
          <td>${fatura.dataVencimento}</td>
          <td>${fatura.status === "P" ? "Paga" : fatura.status === "A" ? "Aberta" : "Bloqueada"}</td>
          <td>${fatura.dataPagamento || "-"}</td>
          <td>
            ${fatura.status !== "P" ? `<button onclick="registrarPagamento(${fatura.id})">Pagar</button>` : ""}
          </td>
        `;

        tbody.appendChild(tr);
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

