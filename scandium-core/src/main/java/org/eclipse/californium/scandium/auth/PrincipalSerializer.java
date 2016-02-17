package org.eclipse.californium.scandium.auth;

import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.security.Principal;

import javax.security.auth.x500.X500Principal;

import org.eclipse.californium.scandium.util.DatagramReader;
import org.eclipse.californium.scandium.util.DatagramWriter;

public final class PrincipalSerializer {

	private static final int TYPE_X500_PRINCIPAL = 0x01;
	private static final int TYPE_PSK_PRINCIPAL = 0x02;
	private static final int TYPE_RPK_PRINCIPAL = 0x03;
	
	private PrincipalSerializer() {
	}

	public static void serialize(Principal principal, DatagramWriter writer) {
		if (principal == null) {
			throw new NullPointerException("Prinicpal must not be null");
		} else if (writer == null) {
			throw new NullPointerException("Writer must not be null");
		} else if (principal instanceof PreSharedKeyIdentity) {
			serializeKey((PreSharedKeyIdentity) principal, writer);
		} else if (principal instanceof RawPublicKeyIdentity) {
			serializeSubjectInfo((RawPublicKeyIdentity) principal, writer);
		} else if (principal instanceof X500Principal) {
			serializeSubjectName((X500Principal) principal, writer);
		}
	}

	private static void serializeSubjectName(X500Principal principal, DatagramWriter writer) {
		writer.write(TYPE_X500_PRINCIPAL, 8);
		writeBytes(principal.getName().getBytes(StandardCharsets.UTF_8), writer);
	}

	private static void serializeKey(PreSharedKeyIdentity principal, DatagramWriter writer) {
		writer.write(TYPE_PSK_PRINCIPAL, 8);
		writeBytes(principal.getName().getBytes(StandardCharsets.UTF_8), writer);
	}

	private static void serializeSubjectInfo(RawPublicKeyIdentity principal, DatagramWriter writer) {
		writer.write(TYPE_RPK_PRINCIPAL, 8);
		writeBytes(principal.getSubjectInfo(), writer);
	}

	private static void writeBytes(byte[] bytesToWrite, DatagramWriter writer) {
		writer.write(bytesToWrite.length, 16);
		writer.writeBytes(bytesToWrite);
	}

	public static Principal deserialize(DatagramReader reader) throws GeneralSecurityException {
		int type = reader.read(8);
		switch(type) {
		case TYPE_X500_PRINCIPAL:
			return deserializeSubjectName(reader);
		case TYPE_PSK_PRINCIPAL:
			return deserializeKey(reader);
		case TYPE_RPK_PRINCIPAL:
			return deserializeSubjectInfo(reader);
		default:
			return null;
		}
	}

	private static X500Principal deserializeSubjectName(DatagramReader reader) {
		return new X500Principal(new String(readBytes(reader)));
	};

	private static PreSharedKeyIdentity deserializeKey(DatagramReader reader) {
		return new PreSharedKeyIdentity(new String(readBytes(reader)));
	};

	private static RawPublicKeyIdentity deserializeSubjectInfo(DatagramReader reader) throws GeneralSecurityException {
		byte[] subjectInfo = readBytes(reader);
		return new RawPublicKeyIdentity(subjectInfo);
	}

	private static byte[] readBytes(DatagramReader reader) {
		int length = reader.read(16);
		return reader.readBytes(length);
	}
}
