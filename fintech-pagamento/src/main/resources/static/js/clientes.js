document.addEventListener("DOMContentLoaded", function () {
  carregarClientes(); // carregar todos inicialmente

  // Botões ações
  document.getElementById("btn-listar-todos").addEventListener("click", () => carregarClientes());
  document.getElementById("btn-bloqueados").addEventListener("click", () => listarBloqueados());
  document.getElementById("btn-novo-cliente").addEventListener("click", () => mostrarFormulario());

  // Formulário de cadastro
  document.getElementById("cliente-form").addEventListener("submit", function(event) {
    event.preventDefault();
    salvarCliente();
  });
});

function carregarClientes() {
  fetch("/clientes")
    .then(response => response.ok ? response.json() : Promise.reject("Erro ao carregar clientes."))
    .then(clientes => popularTabela(clientes))
    .catch(erro => alert(erro));
}

function listarBloqueados() {
  fetch("/clientes/bloqueados")
    .then(response => response.ok ? response.json() : Promise.reject("Erro ao carregar clientes bloqueados."))
    .then(clientes => popularTabela(clientes))
    .catch(erro => alert(erro));
}

function buscarPorId() {
  const id = document.getElementById("busca-id").value.trim();
  if (!id) {
    alert("Digite um ID para buscar.");
    return;
  }
  fetch(`/clientes/${id}`)
    .then(response => {
      if (response.ok) return response.json();
      else throw new Error("Cliente não encontrado.");
    })
    .then(cliente => popularTabela([cliente]))
    .catch(erro => alert(erro.message));
}

function buscarPorNome() {
  const nome = document.getElementById("busca-nome").value.trim();
  if (!nome) {
    alert("Digite um nome para buscar.");
    return;
  }
  fetch(`/clientes/buscarPorNome?nome=${encodeURIComponent(nome)}`)
    .then(response => response.ok ? response.json() : Promise.reject("Erro na busca por nome."))
    .then(clientes => popularTabela(clientes))
    .catch(erro => alert(erro));
}

function buscarPorCpf() {
  const cpf = document.getElementById("busca-cpf").value.trim();
  if (!cpf) {
    alert("Digite um CPF para buscar.");
    return;
  }
  fetch(`/clientes/buscarPorCpf?cpf=${encodeURIComponent(cpf)}`)
    .then(response => response.ok ? response.json() : Promise.reject("Erro na busca por CPF."))
    .then(cliente => {
      popularTabela(cliente ? [cliente] : []);
    })
    .catch(erro => alert(erro));
}

function popularTabela(clientes) {
  const tbody = document.querySelector("#tabela-clientes tbody");
  tbody.innerHTML = "";

  if (!clientes || clientes.length === 0) {
    tbody.innerHTML = `<tr><td colspan="6">Nenhum cliente encontrado.</td></tr>`;
    return;
  }

  clientes.forEach(cliente => {
    const tr = document.createElement("tr");
    tr.innerHTML = `
      <td>${cliente.nome}</td>
      <td>${cliente.cpf}</td>
      <td>${calcularIdade(cliente.dataNascimento)}</td>
      <td>${cliente.statusBloqueio === "B" ? "Bloqueado" : "Ativo"}</td>
      <td>R$ ${cliente.limiteCredito.toFixed(2)}</td>
      <td><button onclick="verFaturas(${cliente.id})">Ver Faturas</button></td>
    `;
    tbody.appendChild(tr);
  });
}

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

function mostrarFormulario() {
  document.getElementById("form-cadastro").style.display = "block";
}

function cancelarCadastro() {
  document.getElementById("form-cadastro").style.display = "none";
  document.getElementById("cliente-form").reset();
}

function salvarCliente() {
  const cliente = {
    nome: document.getElementById("nome").value,
    cpf: document.getElementById("cpf").value,
    dataNascimento: document.getElementById("dataNascimento").value,
    statusBloqueio: document.getElementById("status").value,
    limiteCredito: parseFloat(document.getElementById("limite").value)
  };

  fetch("/clientes", {
    method: "POST",
    headers: {
      "Content-Type": "application/json"
    },
    body: JSON.stringify(cliente)
  })
  .then(response => {
    if (response.ok) {
      alert("Cliente salvo com sucesso!");
      document.getElementById("form-cadastro").style.display = "none";
      carregarClientes();
    } else {
      alert("Erro ao salvar cliente.");
    }
  })
  .catch(() => alert("Erro de comunicação com o servidor."));
}


