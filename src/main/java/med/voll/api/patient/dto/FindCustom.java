package med.voll.api.patient.dto;

import med.voll.api.patient.Patient;

public record FindCustom(Long id, String nome, String email, String cpf) {
	
	public FindCustom(Patient patient) {
		this(patient.getId(), patient.getNome(), patient.getEmail(), patient.getCpf());
	}
}
