/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao.interfaces;

import domain.RegistrationKey;

public interface RegistrationKeyDao extends BaseDao<RegistrationKey> {
    RegistrationKey findKey(String keyName);
}
