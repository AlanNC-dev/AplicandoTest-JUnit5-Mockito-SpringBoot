package com.pruebas.banco.servicio;

import java.math.BigDecimal;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.pruebas.banco.entidades.Banco;
import com.pruebas.banco.entidades.Cuenta;
import com.pruebas.banco.repositorios.BancoRepositorio;
import com.pruebas.banco.repositorios.CuentaRepositorio;

@Service
public class CuentaServicioImpl implements CuentaServicio {

    @Autowired
    private CuentaRepositorio cuentaRepositorio;

    @Autowired
    private BancoRepositorio bancoRepositorio;

    @Override
    @Transactional(readOnly = true)
    public List<Cuenta> listAll() {
       return cuentaRepositorio.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Cuenta findById(Long id) {
        return cuentaRepositorio.findById(id).orElseThrow();
    }

    @Override
    @Transactional
    public Cuenta save(Cuenta cuenta) {
        return cuentaRepositorio.save(cuenta);
    }

    @Override
    @Transactional(readOnly = true)
    public int revisarTtotalDetransferencias (Long bancoId) {
        Banco banco = bancoRepositorio.findById(bancoId).orElseThrow();
        return banco.getTotalTransferencias();
    }

    @Override
    @Transactional(readOnly = true)
    public BigDecimal revisarSaldo (Long cuentaId) {
        Cuenta cuenta = cuentaRepositorio.findById(cuentaId).orElseThrow();
        return cuenta.getSaldo();
    }

    @Override
    public void transferirDinero(Long numeroCuentaOrigen, Long numeroCuentaDestino, BigDecimal monto, Long bancoId) {
        Cuenta cuentaOrigen = cuentaRepositorio.findById(numeroCuentaOrigen).orElseThrow();
        cuentaOrigen.realizarDebito(monto);
        cuentaRepositorio.save(cuentaOrigen);

        Cuenta cuentaDestino = cuentaRepositorio.findById(numeroCuentaDestino).orElseThrow();
        cuentaDestino.realizarCredito(monto);
        cuentaRepositorio.save(cuentaDestino);

        Banco banco = bancoRepositorio.findById(bancoId).orElseThrow();
        int totalTransferencias = banco.getTotalTransferencias();
        banco.setTotalTransferencias(++totalTransferencias);
        bancoRepositorio.save(banco);
    }

        

    
}
